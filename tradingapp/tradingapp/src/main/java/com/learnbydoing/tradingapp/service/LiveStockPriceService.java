package com.learnbydoing.tradingapp.service;

import com.learnbydoing.tradingapp.entity.LiveStockPrice;

public interface LiveStockPriceService {
    void updateLiveStockPrice(String stockSymbol);
    LiveStockPrice getLiveStockPrice(int stockId);
}
