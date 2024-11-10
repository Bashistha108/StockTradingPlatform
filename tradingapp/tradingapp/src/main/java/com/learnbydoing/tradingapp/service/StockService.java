package com.learnbydoing.tradingapp.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.learnbydoing.tradingapp.entity.Stock;
import com.learnbydoing.tradingapp.repository.StockRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * ObjectMapper is used to parse the JSON response into a JsonNode tree.
 * The time series data is retrieved by accessing the Time Series (5min) field in the JSON.
 * Latest Timestamp is fetched dynamically from the fieldNames iterator. It will fetch the most recent time series entry.
 * Extracting the stock price: The livePrice is extracted from the 4. close key under the latest timestamp.
 */

@Service  // Marks this class as a Spring Service, making it available for dependency injection
public class StockService {

    private final StockRepository stockRepository;

    public StockService( StockRepository stockRepository){
        this.stockRepository = stockRepository;
    }

    public Stock getStockById(int id){
        return stockRepository.findById(id).orElseThrow(()->new NoSuchElementException());
    }


    public Stock getStockBySymbol(String symbol){
        return stockRepository.findByStockSymbol(symbol);
    }

    public Stock getStockByName(String name){
        return stockRepository.findByStockName(name);
    }
    public List<Stock> getAllStocks(){
        return stockRepository.findAll();
    }

    public Stock addStock(Stock stock){
        return stockRepository.save(stock);
    }

    public Stock updateStock(int id, Stock stock){
        if(stockRepository.existsById(id)){
            stock.setId(id);
            return stockRepository.save(stock);
        }
        return null;
    }

    public boolean deleteStock(int id){
        if(stockRepository.existsById(id)){
            stockRepository.deleteById(id);
            return true;
        }

        return false;
    }



}
