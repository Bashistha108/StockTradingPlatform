 package com.learnbydoing.tradingapp.service;

import com.learnbydoing.tradingapp.entity.StockPriceHistory;
import com.learnbydoing.tradingapp.repository.StockPriceHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

 @Service
public class StockPriceHistoryServiceImpl implements StockPriceHistoryService{

    private final StockPriceHistoryRepository stockPriceHistoryRepository;
    private final AlphaVantageClient alphaVantageClient;
    private final StockService stockService;

    @Autowired
    public StockPriceHistoryServiceImpl(StockPriceHistoryRepository stockPriceHistoryRepository, StockService stockService, AlphaVantageClient alphaVantageClient){
        this.stockPriceHistoryRepository = stockPriceHistoryRepository;
        this.alphaVantageClient = alphaVantageClient;
        this.stockService =stockService;
    }

    @Override
    @Transactional
    public void updateStockPriceHistory(String stockSymbol) {
        // Fetch historical prices from Alpha Vantage
        List<StockPriceHistory> historyData = alphaVantageClient.getHistoricalPrices(stockSymbol);

        // Assume Stock ID can be derived from stock symbol
        int stockId = stockService.getStockIdBySymbol(stockSymbol);

        // Save each historical record in the repository
        for (StockPriceHistory record : historyData) {
            record.setStockId(stockId);
            stockPriceHistoryRepository.save(record);
        }
    }

    @Override
    public List<StockPriceHistory> getStockPriceHistory(int stockId) {
        return stockPriceHistoryRepository.findByStockId(stockId);
    }
}
