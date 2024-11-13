package com.learnbydoing.tradingapp.service;
import com.learnbydoing.tradingapp.entity.Stock;
import com.learnbydoing.tradingapp.entity.StockPriceHistory;
import com.learnbydoing.tradingapp.repository.StockPriceHistoryRepository;
import com.learnbydoing.tradingapp.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class StockPriceHistoryServiceImpl implements StockPriceHistoryService{

    private final StockPriceHistoryRepository stockPriceHistoryRepository;
    private final StockRepository stockRepository;
    private final AlphaVantageService alphaVantageService;

    @Autowired
    public StockPriceHistoryServiceImpl(StockPriceHistoryRepository stockPriceHistoryRepository,
                                        StockRepository stockRepository,
                                        AlphaVantageService alphaVantageService) {
        this.stockPriceHistoryRepository = stockPriceHistoryRepository;
        this.stockRepository = stockRepository;
        this.alphaVantageService = alphaVantageService;
    }

    @Override
    @Transactional
    public void updateStockPriceHistory(String stockSymbol) {
        // Retrieve stock entity using stock symbol
        Stock stock = stockRepository.findByStockSymbol(stockSymbol);
        if (stock == null) {
            throw new NoSuchElementException("Stock with symbol " + stockSymbol + " not found.");
        }
        // Use AlphaVantageService to fetch and store historical data. Will also save to the db
        alphaVantageService.fetchAndStoreHistoricalData(stockSymbol);
    }

    @Override
    public List<StockPriceHistory> getStockPriceHistory(int stockId) {
        return stockPriceHistoryRepository.findByStockId(stockId);
    }
}






























