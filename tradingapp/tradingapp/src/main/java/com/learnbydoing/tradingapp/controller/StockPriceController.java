package com.learnbydoing.tradingapp.controller;

import com.learnbydoing.tradingapp.entity.LiveStockPrice;
import com.learnbydoing.tradingapp.entity.StockPriceHistory;
import com.learnbydoing.tradingapp.service.LiveStockPriceService;
import com.learnbydoing.tradingapp.service.StockPriceHistoryService;
import com.learnbydoing.tradingapp.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stocks")
public class StockPriceController {

    private final LiveStockPriceService liveStockPriceService;
    private final StockPriceHistoryService stockPriceHistoryService;
    private final StockService stockService;

    @Autowired
    public StockPriceController(LiveStockPriceService liveStockPriceService,
                                StockPriceHistoryService stockPriceHistoryService,
                                StockService stockService){
        this.liveStockPriceService = liveStockPriceService;
        this.stockPriceHistoryService = stockPriceHistoryService;
        this.stockService = stockService;
    }

    @GetMapping("/live/{symbol}")
    public LiveStockPrice getLivePrice(@PathVariable String symbol){
        int id = stockService.getStockIdBySymbol(symbol);
        return liveStockPriceService.getLiveStockPrice(id);
    }
    @PostMapping("/live/update/{symbol}")
    public void updateLivePrice(@PathVariable String symbol){
        liveStockPriceService.updateLiveStockPrice(symbol);
    }
    @GetMapping("/history/{symbol}")
    public List<StockPriceHistory> getHistory(@PathVariable String symbol) {
        int id =stockService.getStockIdBySymbol(symbol);
        return stockPriceHistoryService.getStockPriceHistory(id);
    }

    @PostMapping("/history/update/{symbol}")
    public void updateHistory(@PathVariable String symbol) {
        stockPriceHistoryService.updateStockPriceHistory(symbol);
    }
}
