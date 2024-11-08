package com.learnbydoing.tradingapp.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Iterator;
/**
 * ObjectMapper is used to parse the JSON response into a JsonNode tree.
 * The time series data is retrieved by accessing the Time Series (5min) field in the JSON.
 * Latest Timestamp is fetched dynamically from the fieldNames iterator. It will fetch the most recent time series entry.
 * Extracting the stock price: The livePrice is extracted from the 4. close key under the latest timestamp.
 */

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
           //Constructing url to fetch the stock data
            String url = String.format("%s?function=TIME_SERIES_INTRADAY&symbol=%s&interval=5min&apikey=%s", BASE_URL, symbol, API_KEY);

            // Fetch JSON response as String
            String jsonResponse = restTemplate.getForObject(url, String.class);

            // Parsing the JSON response to extract the latest stock price
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(jsonResponse);

            //Get the time series node
            JsonNode timeSeries = rootNode.path("Time Series (5min)");

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
}
