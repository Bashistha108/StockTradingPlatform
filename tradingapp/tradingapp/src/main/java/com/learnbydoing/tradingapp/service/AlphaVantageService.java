package com.learnbydoing.tradingapp.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Iterator;

@Service
public class AlphaVantageService {

    @Value("${alphavantage.api.key}")
    public String apiKey;

    private final RestTemplate restTemplate;

    @Autowired
    public AlphaVantageService(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    public String getLiveStockPrice(String symbol){
        String url = UriComponentsBuilder.fromHttpUrl("https://www.alphavantage.co/query")
                .queryParam("function", "TIME_SERIES_INTRADAY")
                .queryParam("symbol", symbol)
                .queryParam("interval", "5min")
                .queryParam("apiKey", apiKey)
                .toUriString();

        //Making the request to alpha vantage
        String response = restTemplate.getForObject(url, String.class);
        return response;
    }

    public String getHistoricalStockData(String symbol){
        String url = UriComponentsBuilder.fromHttpUrl("https://www.alphavantage.co/query")
                .queryParam("function", "TIME_SERIES_DAILY")
                .queryParam("symbol", symbol)
                .queryParam("apiKey", apiKey)
                .toUriString();

        //Making resquest to alpha vantage to get the historical data
        String response = restTemplate.getForObject(url, String.class);
        return response;
    }

}

/*
    // Get live stock price for a given symbol
    public String getLiveStockPrice(String symbol){
        try{
            //Constructing url to fetch the stock data
            String url = String.format("%s?function=TIME_SERIES_INTRADAY&symbol=%s&interval=30min&apikey=%s", BASE_URL, symbol, API_KEY);

            // Fetch JSON response as String
            String jsonResponse = restTemplate.getForObject(url, String.class);

            // Parsing the JSON response to extract the latest stock price
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(jsonResponse);

            JsonNode errorNode = rootNode.path("Note");
            if (!errorNode.isMissingNode()) {
                return "API Rate Limit Exceeded: " + errorNode.asText();
            }

            //Get the time series node
            JsonNode timeSeries = rootNode.path("Time Series (30min)");

            //Get the latest timestamp (latest available price)
            Iterator<String> fieldNames = timeSeries.fieldNames();
            String latestTimeStamp = fieldNames.hasNext() ? fieldNames.next() : null;

            if(latestTimeStamp != null){
                JsonNode latestData = timeSeries.path(latestTimeStamp);
                //AlphaVantage: 1-open, 2-high, 3-low, 4-close, 5-volume. Specific key for different data points
                String livePrice = latestData.path("4. close").asText();  //Extract the close price
                return livePrice;
            }
            else {
                return "Error: No stock data found for symbol " + symbol;
            }

        }
        catch (Exception e){
            return "Error fetching stock data. Please try again later.";
        }

    }
    */