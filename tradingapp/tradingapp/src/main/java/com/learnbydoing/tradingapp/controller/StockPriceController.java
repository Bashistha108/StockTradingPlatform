package com.learnbydoing.tradingapp.controller;

import com.learnbydoing.tradingapp.entity.LiveStockPrice;
import com.learnbydoing.tradingapp.entity.StockPriceHistory;
import com.learnbydoing.tradingapp.service.AlphaVantageService;
import com.learnbydoing.tradingapp.service.LiveStockPriceService;
import com.learnbydoing.tradingapp.service.StockPriceHistoryService;
import com.learnbydoing.tradingapp.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stocks")
public class StockPriceController {

    private final AlphaVantageService alphaVantageService;
    private final StockPriceHistoryService stockPriceHistoryService;

    @Autowired
    public StockPriceController(AlphaVantageService alphaVantageService, StockPriceHistoryService stockPriceHistoryService){
        this.alphaVantageService = alphaVantageService;
        this.stockPriceHistoryService = stockPriceHistoryService;
    }

    @GetMapping("/live/{symbol}")
    public LiveStockPrice getLivePrice(@PathVariable String symbol){
        //update the live price in the db
        alphaVantageService.updateLiveStockPrice(symbol);
        //Retrieve and return the updated live price
        return alphaVantageService.getLiveStockPrice(symbol);
    }

    /* Same as update/symbol
    @PostMapping("/history/{symbol}")
    public String updateStockHistory(@PathVariable String symbol){
        alphaVantageService.fetchAndStoreHistoricalData(symbol);
        return "Historical data for "+symbol+" updated succesfully.";
    }
 */
    @PostMapping("/update/{symbol}")
    public String updateHistoricalData(@PathVariable String symbol) {
        try {
            stockPriceHistoryService.updateStockPriceHistory(symbol);
            return "Historical data updated successfully for the symbol: "+symbol;
        } catch (Exception e){
            return "Failed to update historical data for symbol: "+symbol+"-"+e.getMessage();
        }
    }

    @GetMapping("/{stockId}")
    public List<StockPriceHistory> getHistoricalData(@PathVariable int stockId){
        return stockPriceHistoryService.getStockPriceHistory(stockId);
    }

}




















