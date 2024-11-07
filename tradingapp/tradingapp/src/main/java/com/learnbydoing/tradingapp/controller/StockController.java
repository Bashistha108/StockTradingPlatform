package com.learnbydoing.tradingapp.controller;

import com.learnbydoing.tradingapp.service.StockService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class StockController {

    private final StockService stockService;

    public StockController(StockService stockService){
        this.stockService = stockService;
    }

    // API endpoint to get stock price based on the symbol
    @GetMapping("/stock-price")
    public String getStockPrice(@RequestParam String symbol , Model model){
        String stockPriceData = stockService.getLiveStockPrice(symbol);
        model.addAttribute("symbol", symbol);
        model.addAttribute("stockPriceData", stockPriceData);

        return "stock/stock-price";
    }

}
