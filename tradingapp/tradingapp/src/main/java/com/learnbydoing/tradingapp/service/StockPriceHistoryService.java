package com.learnbydoing.tradingapp.service;

import com.learnbydoing.tradingapp.entity.StockPriceHistory;

import java.util.List;

public interface StockPriceHistoryService {
    void updateStockPriceHistory(String stockSymbol);
    List<StockPriceHistory> getStockPriceHistory(int stockId);
}
