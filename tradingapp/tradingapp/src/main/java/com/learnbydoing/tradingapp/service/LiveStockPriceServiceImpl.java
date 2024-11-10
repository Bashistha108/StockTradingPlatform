package com.learnbydoing.tradingapp.service;

import com.learnbydoing.tradingapp.entity.LiveStockPrice;
import com.learnbydoing.tradingapp.repository.LiveStockPriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LiveStockPriceServiceImpl implements LiveStockPriceService{

    private final LiveStockPriceRepository liveStockPriceRepository;
    private final AlphaVantageClient alphaVantageClient;
    private final StockService stockService;
    @Autowired
    public LiveStockPriceServiceImpl(LiveStockPriceRepository liveStockPriceRepository, AlphaVantageClient alphaVantageClient, StockService stockService){
        this.liveStockPriceRepository = liveStockPriceRepository;
        this.alphaVantageClient = alphaVantageClient;
        this.stockService = stockService;
    }


    @Override
    @Transactional
    public void updateLiveStockPrice(String stockSymbol) {
        //Fetch live price from Alpha Vantage
        double currentPrice = alphaVantageClient.getLivePrice(stockSymbol);

        int stockId = stockService.getStockIdBySymbol(stockSymbol);

        //Update or save live stock price
        LiveStockPrice liveStockPrice = new LiveStockPrice();
        liveStockPrice.setStockId(stockId);
        liveStockPrice.setCurrentPrice(currentPrice);
        liveStockPriceRepository.save(liveStockPrice);
    }

    @Override
    public LiveStockPrice getLiveStockPrice(int stockId) {
        return liveStockPriceRepository.findById(stockId).orElse(null);
    }
}






















