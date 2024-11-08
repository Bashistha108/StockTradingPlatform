package com.learnbydoing.tradingapp.controller;

import com.learnbydoing.tradingapp.service.StockService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class StockController {

    private final StockService stockService;

    public StockController(StockService stockService){
        this.stockService = stockService;
    }

    // API endpoint to get stock price based on the symbol

    @PostMapping("/search")
    public String searchStock(@RequestParam("symbol") String symbol, Model model){
        String stockData = stockService.getLiveStockPrice(symbol);
        model.addAttribute("stockData", stockData);
        model.addAttribute("symbol", symbol);
        return "stock/stock-price";
    }

}
