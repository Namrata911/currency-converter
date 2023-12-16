package com.example.app.controller;

import com.example.app.controller.dto.InputDTO;
import com.example.app.controller.dto.OutputDTO;
import com.example.app.entity.Currency;
import com.example.app.service.CurrencyConversionService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
@Slf4j
@AllArgsConstructor
public class CurrencyConverterController {

    private CurrencyConversionService service;

    @GetMapping(path = {"/", "/index"})
    public String index(Model model) {
        log.debug("Returning index page.");
        List<Currency> currencyList = new ArrayList<>();
        Collections.addAll(currencyList, Currency.values());
        model.addAttribute("currencyList", currencyList);
        model.addAttribute("inputDTO", new InputDTO());
        return ("index.html");
    }

    @PostMapping("/convert")
    public String handleConversion(String sourceCurrency, String targetCurrency, BigDecimal amount, Model model) throws ParseException {
        log.info("handler called with values {} , {}, {}", sourceCurrency, targetCurrency, amount);
        model.addAttribute("outputDTO", new OutputDTO(sourceCurrency, targetCurrency, amount,
                service.convert(Currency.getCurrencyForCode(sourceCurrency), Currency.getCurrencyForCode(targetCurrency), amount)));
        return "result.html";
    }

}
