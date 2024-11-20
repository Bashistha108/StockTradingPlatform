package com.learnbydoing.tradingapp.controller;

import com.learnbydoing.tradingapp.entity.LiveStockPrice;
import com.learnbydoing.tradingapp.service.AlphaVantageService;
import com.learnbydoing.tradingapp.service.FinnhubService;
import com.learnbydoing.tradingapp.service.StockPriceHistoryService;
import com.learnbydoing.tradingapp.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/stocks")
public class StockPriceController {

    private final FinnhubService finnhubService;
    private final StockPriceHistoryService stockPriceHistoryService;
    private final StockService stockService;

    @Autowired
    public StockPriceController(FinnhubService finnhubService, StockPriceHistoryService stockPriceHistoryService, StockService stockService){
        this.finnhubService = finnhubService;
        this.stockPriceHistoryService = stockPriceHistoryService;
        this.stockService = stockService;

    }


    // API endpoint to get stock price based on the symbol
    // POST: Search for Stock
    @PostMapping("/search")
    public String searchStock(@RequestParam("symbol") String symbol, Model model){
        //update the live price in the db
        finnhubService.updateLiveStockPrice(symbol);
        //Retrieve and return the updated live price
        LiveStockPrice liveStockPrice = finnhubService.getLiveStockPrice(symbol);
        double currentPrice = liveStockPrice.getCurrentPrice();
        model.addAttribute("currentPrice", currentPrice);
        model.addAttribute("symbol", symbol);
        return "stock/stock-price";
    }

    @PostMapping("/update/{symbol}")
    public String updateHistoricalData(@PathVariable("symbol") String symbol) {
        try {
            stockPriceHistoryService.updateStockPriceHistory(symbol);
            System.out.println("Historical data updated successfully for the symbol: "+symbol);
        } catch (Exception e){
            System.out.println("Failed to update historical data for symbol: "+symbol+"-"+e.getMessage());
        }
        return "redirect:/stocks/search/"+symbol;
    }
    // GET: Display Search Results
    @GetMapping("/search/{symbol}")
    public String showStockPricePageSupporter(@PathVariable("symbol") String symbol, Model model){
        double currentPrice = finnhubService.getLiveStockPrice(symbol).getCurrentPrice();
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



//    @GetMapping("/{symbol}")
//    public List<StockPriceHistory> getHistoricalData(@PathVariable String symbol){
//        int stockId = stockService.getStockIdBySymbol(symbol);
//        return stockPriceHistoryService.getStockPriceHistory(stockId);
//    }

}




















