package com.example.app.controller;


import com.example.app.controller.dto.InputDTO;
import com.example.app.entity.Currency;
import com.example.app.service.CurrencyConversionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class CurrencyConverterControllerTest {

    @Mock
    private CurrencyConversionService currencyConversionService;

    @Mock
    private Model model;

    @InjectMocks
    private CurrencyConverterController controller;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/templates/");
        viewResolver.setSuffix(".html");

        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setViewResolvers(viewResolver)
                .build();
    }

    @Test
    void testIndex() throws Exception {
        // Arrange
        List<Currency> currencyList = new ArrayList<>();
        Collections.addAll(currencyList, Currency.values());

        // Act and Assert
        mockMvc.perform(get("/index"))
                .andExpect(status().isOk())
                .andExpect(view().name("index.html"))
                .andExpect(model().attribute("currencyList", currencyList))
                .andExpect(model().attribute("inputDTO", new InputDTO()));
    }

    @Test
    void testHandleConversion() throws Exception {
        // Arrange
        String sourceCurrency = "USD";
        String targetCurrency = "EUR";
        BigDecimal amount = BigDecimal.TEN;
        when(currencyConversionService.convert(any(), any(), any())).thenReturn(BigDecimal.valueOf(15.0));

        // Act and Assert
        mockMvc.perform(post("/convert")
                        .param("sourceCurrency", sourceCurrency)
                        .param("targetCurrency", targetCurrency)
                        .param("amount", amount.toString()))
                .andExpect(status().isOk())
                .andExpect(view().name("result.html"))
                .andExpect(model().attributeExists("outputDTO"));
    }
}
