package com.learnbydoing.tradingapp.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.learnbydoing.tradingapp.entity.LiveStockPrice;
import com.learnbydoing.tradingapp.entity.Stock;
import com.learnbydoing.tradingapp.entity.StockPriceHistory;
import com.learnbydoing.tradingapp.repository.LiveStockPriceRepository;
import com.learnbydoing.tradingapp.repository.StockPriceHistoryRepository;
import com.learnbydoing.tradingapp.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.NoSuchElementException;

@Service
public class AlphaVantageService {

    private final AlphaVantageClient alphaVantageClient;
    private final LiveStockPriceRepository liveStockPriceRepository;
    private final StockRepository stockRepository;
    private final StockService stockService;
    private final StockPriceHistoryRepository stockPriceHistoryRepository;
    @Autowired
    public AlphaVantageService(AlphaVantageClient alphaVantageClient, LiveStockPriceRepository liveStockPriceRepository, StockRepository stockRepository,
                               StockService stockService, StockPriceHistoryRepository stockPriceHistoryRepository){
        this.alphaVantageClient = alphaVantageClient;
        this.liveStockPriceRepository = liveStockPriceRepository;
        this.stockRepository = stockRepository;
        this.stockService = stockService;
        this.stockPriceHistoryRepository = stockPriceHistoryRepository;
    }

    @Transactional
    public void updateLiveStockPrice(String stockSymbol){

        Stock stock = stockRepository.findByStockSymbol(stockSymbol);
        if(stock == null){
            System.err.println("Stock with symbol "+stockSymbol+" not found.");
            return;
        }

        double livePrice = alphaVantageClient.getLivePrice(stockSymbol);

        //find live stock price entry by stock id
        LiveStockPrice stockPrice = liveStockPriceRepository.findByStockId(stock.getId());
        if(stockPrice == null){
            //Create new entry if not found
            stockPrice = new LiveStockPrice();
            stockPrice.setStockId(stock.getId());
        }
        stockPrice.setCurrentPrice(livePrice);
        stockPrice.setLastUpdated(String.valueOf(new Timestamp(System.currentTimeMillis())));
        liveStockPriceRepository.save(stockPrice);
    }

    public LiveStockPrice getLiveStockPrice(String stockSymbol){
        Stock stock = stockRepository.findByStockSymbol(stockSymbol);
        return stock != null ? liveStockPriceRepository.findByStockId(stock.getId()) : null;
    }

    public void fetchAndStoreHistoricalData(String stockSymbol) {
        //Fetch the Stock entity by symbol
        Stock stock = stockRepository.findByStockSymbol(stockSymbol);
        if (stock == null) {
            System.err.println("Stock with symbol " + stockSymbol + " not found.");
            return;
        }

        JsonNode timeSeriesData = alphaVantageClient.getHistoricalData(stockSymbol);
        if (timeSeriesData != null && timeSeriesData.isObject()) {
            timeSeriesData.fields().forEachRemaining(entry -> {
                String dateStr = entry.getKey();
                JsonNode dailyData = entry.getValue();

                // Check if the record already exists in the DB for the same stock and date
                if (stockPriceHistoryRepository.existsByStockIdAndDate(stock.getId(), Date.valueOf(dateStr))) {
                    System.out.println("Historical data for " + stockSymbol + " on " + dateStr + " already exists. Skipping.");
                    return; // Skip saving if record exists
                }

                // Create a new StockPriceHistory record
                StockPriceHistory history = new StockPriceHistory();
                history.setStockId(stock.getId()); // Set the stockId as the foreign key
                history.setDate(Date.valueOf(dateStr)); // Set the date
                history.setOpenPrice(dailyData.path("1. open").asDouble());
                history.setClosePrice(dailyData.path("4. close").asDouble());
                history.setHighPrice(dailyData.path("2. high").asDouble());
                history.setLowPrice(dailyData.path("3. low").asDouble());
                history.setVolume(dailyData.path("5. volume").asLong());

                // Save the record in the repository
                stockPriceHistoryRepository.save(history);
            });
        }
    }
}

































