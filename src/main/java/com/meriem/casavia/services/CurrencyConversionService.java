package com.meriem.casavia.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

@Service
public class CurrencyConversionService {
    private static final Logger logger = LoggerFactory.getLogger(CurrencyConversionService.class);
    private final String apiKey = "450a1a0bba3256d0d134d23a";

    private final RestTemplate restTemplate = new RestTemplate();
    private final String API_URL_TEMPLATE = "https://v6.exchangerate-api.com/v6/%s/latest/%s";

    public double getConversionRate(String fromCurrency, String toCurrency) {
        String url = String.format(API_URL_TEMPLATE, apiKey, fromCurrency);
        ResponseEntity<Map> response;

        try {
            logger.info("Fetching exchange rate from URL: {}", url);
            response = restTemplate.getForEntity(url, Map.class);
            logger.info("Response from exchange rate API: {}", response.getBody());
        } catch (Exception e) {
            logger.error("Error fetching data from exchange rate API", e);
            throw new RuntimeException("Error fetching data from exchange rate API", e);
        }

        if (response.getBody() == null) {
            logger.error("API response body is null");
            throw new RuntimeException("Invalid response from the exchange rate API: response body is null");
        }

        Map<String, Object> data = (Map<String, Object>) response.getBody();

        if (!"success".equals(data.get("result"))) {
            logger.error("API request failed: {}", data.get("error-type"));
            throw new RuntimeException("Failed to convert currency: " + data.get("error-type"));
        }

        Map<String, Object> rates = (Map<String, Object>) data.get("conversion_rates");

        if (!rates.containsKey(toCurrency)) {
            logger.error("Invalid target currency: {}", toCurrency);
            throw new RuntimeException("No rate found for currency: " + toCurrency);
        }

        try {
            return Double.parseDouble(rates.get(toCurrency).toString());
        } catch (NumberFormatException e) {
            logger.error("Failed to parse conversion rate: {}", rates.get(toCurrency));
            throw new RuntimeException("Failed to parse conversion rate", e);
        }
    }

    public double convertToEuro(double amount, String currency) {
        double conversionRate = getConversionRate(currency, "EUR");
        double result = amount * conversionRate;
        return roundToZeroDecimalPlaces(result);
    }

    public double convertToTunisianDinar(double amount, String currency) {
        double conversionRate = getConversionRate(currency, "TND");
        return amount * conversionRate;
    }
    private double roundToZeroDecimalPlaces(double value) {
        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(0, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
    public double convertPrice(double amount, String fromCurrency, String toCurrency) {
        double conversionRate = getConversionRate(fromCurrency, toCurrency);
        return roundToZeroDecimalPlaces(amount * conversionRate);
    }


}
