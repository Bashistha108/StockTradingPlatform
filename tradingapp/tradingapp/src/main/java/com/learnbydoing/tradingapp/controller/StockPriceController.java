package com.learnbydoing.tradingapp.controller;

import com.learnbydoing.tradingapp.entity.LiveStockPrice;
import com.learnbydoing.tradingapp.entity.StockPriceHistory;
import com.learnbydoing.tradingapp.service.AlphaVantageService;
import com.learnbydoing.tradingapp.service.LiveStockPriceService;
import com.learnbydoing.tradingapp.service.StockPriceHistoryService;
import com.learnbydoing.tradingapp.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/stocks")
public class StockPriceController {

    private final AlphaVantageService alphaVantageService;
    private final StockPriceHistoryService stockPriceHistoryService;

    @Autowired
    public StockPriceController(AlphaVantageService alphaVantageService, StockPriceHistoryService stockPriceHistoryService){
        this.alphaVantageService = alphaVantageService;
        this.stockPriceHistoryService = stockPriceHistoryService;
    }


    // API endpoint to get stock price based on the symbol

    @PostMapping("/search")
    public String searchStock(@RequestParam("symbol") String symbol, Model model){
        //update the live price in the db
        alphaVantageService.updateLiveStockPrice(symbol);
        //Retrieve and return the updated live price
        LiveStockPrice liveStockPrice = alphaVantageService.getLiveStockPrice(symbol);
        double currentPrice = liveStockPrice.getCurrentPrice();
        model.addAttribute("currentPrice", currentPrice);
        model.addAttribute("symbol", symbol);
        return "stock/stock-price";
    }


//    @GetMapping("/live/{symbol}")
//    public LiveStockPrice getLivePrice(@PathVariable String symbol){
//        //update the live price in the db
//        alphaVantageService.updateLiveStockPrice(symbol);
//        //Retrieve and return the updated live price
//        return alphaVantageService.getLiveStockPrice(symbol);
//    }


    @PostMapping("/update/{symbol}")
    public String updateHistoricalData(@PathVariable("symbol") String symbol) {
        try {
            stockPriceHistoryService.updateStockPriceHistory(symbol);
            System.out.println("Historical data updated successfully for the symbol: "+symbol);
        } catch (Exception e){
            System.out.println("Failed to update historical data for symbol: "+symbol+"-"+e.getMessage());
        }
        return "redirect:/stocks/search";
    }

    @GetMapping("/{stockId}")
    public List<StockPriceHistory> getHistoricalData(@PathVariable int stockId){
        return stockPriceHistoryService.getStockPriceHistory(stockId);
    }

}




















