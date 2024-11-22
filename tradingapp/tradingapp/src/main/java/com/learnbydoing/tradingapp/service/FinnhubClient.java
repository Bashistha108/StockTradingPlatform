package com.learnbydoing.tradingapp.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class FinnhubClient {

    // Jackson Objectmapper to map the json in java object
    private  final ObjectMapper objectMapper = new ObjectMapper();

    private final String apiKey = "csv5rf1r01qq28mn1o5gcsv5rf1r01qq28mn1o60";
    private final RestTemplate restTemplate = new RestTemplate();

    /**
     * Fetch live price for a Stock using Finnhub API
     */

    public double getLivePrice(String stockSymbol){
        if(stockSymbol == null || stockSymbol.isEmpty()){
            System.err.println("Error: Stock symbol cannot be empty.");
            return 0.0;
        }

        //Make the Api call to finnhub for real time stock price
        String url =  "https://finnhub.io/api/v1/quote?symbol=" + stockSymbol + "&token=" + apiKey;

        try{
            //Make api call and get the response as a string
            String response = restTemplate.getForObject(url, String.class);

            //Parse the response Json to extract the live price
            JsonNode root = objectMapper.readTree(response);
            double currentPrice = root.path("c").asDouble(); // "c" is the current price in Finnhub API response
            System.out.println("-----------------------------------");
            System.out.println("-----------------------------------");
            System.out.println("Live price for "+stockSymbol+": "+currentPrice);
            System.out.println("-----------------------------------");
            System.out.println("-----------------------------------");

            return currentPrice;
        }catch (Exception e){
            System.err.println("Error fetching live price for symbol: "+stockSymbol+". Error: "+e.getMessage());
            return 0.0;
        }
    }

    /**
    * Fetches historical data for a stock using Finhubb API
     * Returns a JSON node containing the historical price data
    */
    public JsonNode getHistoricalData(String stockSymbol){
        String url = "https://finnhub.io/api/v1/stock/candle?symbol=" + stockSymbol + "&resolution=D&from=1580380800&to=1633641600&token=" + apiKey;
        try{
            //Make the api call and get the response as a string
            String response = restTemplate.getForObject(url, String.class);

            //Parse the response JSON to get the historical data
            JsonNode root = objectMapper.readTree(response);
            return root; // root contains the historical data in JSON format
        }catch (Exception e){
            System.err.println("Error fetching the historical data: "+e.getMessage());
            return null;
        }
    }

}











































