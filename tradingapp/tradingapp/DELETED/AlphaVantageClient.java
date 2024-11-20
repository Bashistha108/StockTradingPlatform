package com.learnbydoing.tradingapp.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.learnbydoing.tradingapp.entity.StockPriceHistory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
/*
    This component will handle API calls to Alpha Vantage, parse the JSON response, and return structured data.
 */
@Component
public class AlphaVantageClient {


    //Jackson ObjecktMApper
    private final ObjectMapper objectMapper = new ObjectMapper();

    private final String apiKey = "P7K9NNS5Y4WRFN11";
    private final RestTemplate restTemplate = new RestTemplate();

    public double getLivePrice(String stockSymbol){

        if (stockSymbol == null || stockSymbol.trim().isEmpty()) {
            System.err.println("Error: Stock symbol cannot be empty.");
            return 0.0;  // Or you can throw an exception if preferred
        }

        // Make Api call to Alpha Vantage and parse response for live price
        String url = "https://www.alphavantage.co/query?function=GLOBAL_QUOTE&symbol="+stockSymbol+"&apikey="+apiKey;
        try{
            //Make the api call and get response as String
            String response = restTemplate.getForObject(url, String.class);

            //Parse the response JSON to extract the price using Jackson
            JsonNode root = objectMapper.readTree(response);
            JsonNode globalQuote = root.path("Global Quote");

            //Extract the live price from the response
            String priceStr = globalQuote.path("05. price").asText();
            return Double.parseDouble(priceStr);
        }
        catch (Exception e){
            System.err.println("Error fetching live price for symbol: "+stockSymbol+". Error: "+e.getMessage());
            return 0.0; //Return a default value or handle error as needed.
        }
    }

    /*
    * Fetches historical price data for a stock by its symbol
    *
    * @param stocksymbol the Stock symbol to fetch the historical data for
    * @return A JsonNode containing the daily time series data.
    *
    * */

    public JsonNode getHistoricalData(String stockSymbol){
        String url = "https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&symbol=" + stockSymbol + "&apikey=" + apiKey;
        try{
            String response = restTemplate.getForObject(url, String.class);
            JsonNode root = new ObjectMapper().readTree(response);
            return root.path("Time Series (Daily)"); //Returns the daily time series JSON node
        }catch (Exception e){
            System.err.println("Error fetching the historical data: "+e.getMessage());
            return null;
        }
    }


}











































