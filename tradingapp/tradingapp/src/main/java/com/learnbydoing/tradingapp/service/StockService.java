package com.learnbydoing.tradingapp.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service  // Marks this class as a Spring Service, making it available for dependency injection
public class StockService {
    private static final String API_KEY = "P7K9NNS5Y4WRFN11";
    private static final String BASE_URL = "https://www.alphavantage.co/query";

    private final RestTemplate restTemplate;

    // Constructor to inject the RestTemplate bean
    public StockService(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    // Get live stock price for a given symbol
    public String getLiveStockPrice(String symbol){
        try{
            String url = String.format("%s?function=TIME_SERIES_INTRADAY&symbol=%s&interval=5min&apikey=%s", BASE_URL, symbol, API_KEY);
            return restTemplate.getForObject(url, String.class);
        }
        catch (Exception e){
            return "Error fetching stock data. Please try again later.";
        }

    }
}
