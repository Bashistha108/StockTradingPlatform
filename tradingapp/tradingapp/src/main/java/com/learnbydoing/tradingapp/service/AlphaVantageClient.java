package com.learnbydoing.tradingapp.service;

import com.learnbydoing.tradingapp.entity.StockPriceHistory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
/*
    This component will handle API calls to Alpha Vantage, parse the JSON response, and return structured data.
 */
@Component
public class AlphaVantageClient {

    private final String apiKey = "P7K9NNS5Y4WRFN11";
    private final RestTemplate restTemplate = new RestTemplate();

    public double getLivePrice(String stockSymbol){
        // Make Api call to Alpha Vantage and parse response for live price
        String url = "https://www.alphavantage.co/query?function=GLOBAL_QUOTE&symbol="+stockSymbol+"&apikey="+apiKey;
        //Process the response to get the live price
        return 100.0; //Placeholder value
    }

    public List<StockPriceHistory> getHistoricalPrices(String stockSymbol){
        // Make API call to Alpha Vantage and parse response for historical prices
        String url = "https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&symbol=" + stockSymbol + "&apikey=" + apiKey;
        // Parse response to a list of StockPriceHistory records
        return List.of(new StockPriceHistory()); //Placeholder list
    }

}
