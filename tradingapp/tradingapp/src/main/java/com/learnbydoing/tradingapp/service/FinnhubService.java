package com.learnbydoing.tradingapp.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.learnbydoing.tradingapp.entity.LiveStockPrice;
import com.learnbydoing.tradingapp.entity.Stock;
import com.learnbydoing.tradingapp.entity.StockPriceHistory;
import com.learnbydoing.tradingapp.repository.LiveStockPriceRepository;
import com.learnbydoing.tradingapp.repository.StockPriceHistoryRepository;
import com.learnbydoing.tradingapp.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.sql.Timestamp;

@Service
public class FinnhubService {

    private final FinnhubClient finnhubClient;
    private final LiveStockPriceRepository liveStockPriceRepository;
    private final StockRepository stockRepository;
    private final StockService stockService;
    private final StockPriceHistoryRepository stockPriceHistoryRepository;

    @Autowired
    public FinnhubService(FinnhubClient finnhubClient,
                          LiveStockPriceRepository liveStockPriceRepository,
                          StockRepository stockRepository,
                          StockService stockService,
                          StockPriceHistoryRepository stockPriceHistoryRepository) {
        this.finnhubClient = finnhubClient;
        this.liveStockPriceRepository = liveStockPriceRepository;
        this.stockRepository = stockRepository;
        this.stockService = stockService;
        this.stockPriceHistoryRepository = stockPriceHistoryRepository;
    }

    /**
     * Updates live stock price using finhubb's api
     */

    @Transactional
    public void updateLiveStockPrice(String stockSymbol){
        Stock stock = stockRepository.findByStockSymbol(stockSymbol);
        if(stock == null){
            System.err.println("Stock with symbol "+stockSymbol+" not found.");
            return;
        }

        double livePrice = finnhubClient.getLivePrice(stockSymbol);

        //Find LiveStockPrice by Stock Id
        LiveStockPrice liveStockPrice = liveStockPriceRepository.findByStockId(stock.getId());
        if(liveStockPrice == null){
            //create new entry if not found
            liveStockPrice = new LiveStockPrice();
            liveStockPrice.setStockId(stock.getId());
        }

        liveStockPrice.setCurrentPrice(livePrice);
        liveStockPrice.setLastUpdated(String.valueOf(new Timestamp(System.currentTimeMillis())));
        liveStockPriceRepository.save(liveStockPrice);

    }

    /**
     * Retrieves live stock price from the repository
     */
    public LiveStockPrice getLiveStockPrice(String stockSymbol) {
        Stock stock = stockRepository.findByStockSymbol(stockSymbol);
        return stock != null ? liveStockPriceRepository.findByStockId(stock.getId()) : null;
    }

    /**
     * Fetches and stores historical data for a stock using Finnhub API
     */
    public void fetchAndStoreHistoricalData(String stockSymbol) {
        // Fetch the Stock entity by symbol
        Stock stock = stockRepository.findByStockSymbol(stockSymbol);
        if (stock == null) {
            System.err.println("Stock with symbol " + stockSymbol + " not found.");
            return;
        }

        JsonNode historicalData = finnhubClient.getHistoricalData(stockSymbol);
        if (historicalData != null) {
            JsonNode timestamps = historicalData.path("t"); // Timestamps
            JsonNode opens = historicalData.path("o");     // Open prices
            JsonNode closes = historicalData.path("c");    // Close prices
            JsonNode highs = historicalData.path("h");     // High prices
            JsonNode lows = historicalData.path("l");      // Low prices
            JsonNode volumes = historicalData.path("v");   // Volumes

            if (timestamps.isArray() && timestamps.size() > 0) {
                for (int i = 0; i < timestamps.size(); i++) {
                    long timestamp = timestamps.get(i).asLong();
                    Date date = new Date(timestamp * 1000L); // Convert UNIX timestamp to Date

                    // Check if the record already exists in the DB for the same stock and date
                    if (stockPriceHistoryRepository.existsByStockIdAndDate(stock.getId(), date)) {
                        System.out.println("Historical data for " + stockSymbol + " on " + date + " already exists. Skipping.");
                        continue;
                    }

                    // Create a new StockPriceHistory record
                    StockPriceHistory history = new StockPriceHistory();
                    history.setStockId(stock.getId());
                    history.setDate(date);
                    history.setOpenPrice(opens.get(i).asDouble());
                    history.setClosePrice(closes.get(i).asDouble());
                    history.setHighPrice(highs.get(i).asDouble());
                    history.setLowPrice(lows.get(i).asDouble());
                    history.setVolume(volumes.get(i).asLong());

                    // Save the record in the repository
                    stockPriceHistoryRepository.save(history);
                }
            } else {
                System.err.println("No historical data available for " + stockSymbol);
            }
        } else {
            System.err.println("Error fetching historical data for " + stockSymbol);
        }
    }

    public double getLivePrice(String stockSymbol){
//        int stockId = stockService.getStockIdBySymbol(stockSymbol);
//        LiveStockPrice liveStockPrice = liveStockPriceRepository.findByStockId(stockId);
//        double currentPrice = finnhubClient.getLivePrice(stockSymbol);
//        liveStockPrice.setCurrentPrice(currentPrice);
//        liveStockPrice.setLastUpdated(String.valueOf(new Timestamp(System.currentTimeMillis())));
//        liveStockPriceRepository.save(liveStockPrice);
        return finnhubClient.getLivePrice(stockSymbol);
    }

    public JsonNode getHistoricalData(String stockSymbol) {
        // Calls the client to get historical data
        return finnhubClient.getHistoricalData(stockSymbol);
    }
}






































