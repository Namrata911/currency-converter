package com.example.app.util;

import com.example.app.entity.Currency;
import com.example.app.exception.ServerErrorException;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.net.SocketTimeoutException;

@Component
@Slf4j
public class CurrencyRateRestClient {

    @Value("${baseUrl}")
    private String baseUrl;
    @Value("${fallbackBaseUrl}")
    private String fallbackBaseUrl;
    @Value("${timeoutMillis}")
    private int timeoutMillis;

    /**
     * Returns the conversion rate to all target currencies from given source sourceCurrency
     *
     * @param sourceCurrency : source sourceCurrency
     * @return Map<Currency, Float> : conversion rate to all target currencies from given source sourceCurrency
     * @throws ParseException : in case of unexpected response.
     */
    @Cacheable("exchangeRates")
    public String fetchRatesForCurrency(Currency sourceCurrency, Currency targetCurrency) throws ParseException {
        ResponseEntity<String> responseEntity;
        try {
            responseEntity = callUrl(baseUrl, sourceCurrency, targetCurrency);
        }catch (ResourceAccessException e){
            log.debug("After timeout using fallback url");
            responseEntity = callUrl(fallbackBaseUrl, sourceCurrency, targetCurrency);
        }

        // if we do not get response we can try fallback URL
        if (!responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            responseEntity = callUrl(fallbackBaseUrl, sourceCurrency, targetCurrency);
        }
        if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(responseEntity.getBody());
            log.debug("JSON {}", json);
            return convertJsonToMap(json);
        }
        throw raiseServerException(sourceCurrency, targetCurrency, baseUrl, responseEntity);
    }

    /**
     * Calls the external API and returns response as String
     * @param baseUrl : url
     * @param sourceCurrency : source currency
     * @param targetCurrency : target currency
     * @return : API response as String
     */
    private ResponseEntity<String> callUrl(String baseUrl, Currency sourceCurrency, Currency targetCurrency) {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(timeoutMillis);
        requestFactory.setReadTimeout(timeoutMillis);
        RestTemplate restTemplate = new RestTemplate(requestFactory);

        String url = baseUrl.replace("{sourceCurrency}", sourceCurrency.getCode())
                .replace("{targetCurrency}", targetCurrency.getCode());
        log.debug("Calling external API : {}", url);
        return restTemplate.getForEntity(url, String.class);
    }

    private String convertJsonToMap(JSONObject json) {
        for (Object key : json.keySet()) {
            if (!((String) key).equalsIgnoreCase("date")) {
                return String.valueOf(json.get(key));
            }
        }
        return null;
    }

    private ServerErrorException raiseServerException(Currency sourceCurrency, Currency targetCurrency, String baseUrl,
                                                      ResponseEntity<String> responseEntity) {
        String url = baseUrl.replace("{sourceCurrency}", sourceCurrency.getCode())
                .replace("{targetCurrency}", targetCurrency.getCode());
        String message = String.format("Invalid Response Received From Upstream API {} : {}", url, responseEntity.getStatusCode());
        log.error(message);
        return new ServerErrorException(message);
    }

}
