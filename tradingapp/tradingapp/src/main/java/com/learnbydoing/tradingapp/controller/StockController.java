package com.learnbydoing.tradingapp.controller;

import com.learnbydoing.tradingapp.entity.Stock;
import com.learnbydoing.tradingapp.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stocks")
public class StockController {

    private final StockService stockService;

    @Autowired
    public StockController(StockService stockService){
        this.stockService = stockService;
    }

    // API endpoint to get stock price based on the symbol
/*
    @PostMapping("/search")
    public String searchStock(@RequestParam("symbol") String symbol, Model model){
        String stockData = stockService.getLiveStockPrice(symbol);
        model.addAttribute("stockData", stockData);
        model.addAttribute("symbol", symbol);
        return "stock/stock-price";
    }

*/

    @PostMapping("/add")
    public Stock addStock(@RequestBody Stock stock){
        String symbol = stock.getStockSymbol().toUpperCase();
        String name = stock.getStockName().toUpperCase();
        stock.setStockSymbol(symbol);
        stock.setStockName(name);
        return stockService.addStock(stock);
    }

    @GetMapping("/get-all")
    public List<Stock> getAllStocks(){
        return stockService.getAllStocks();
    }

    @GetMapping("/search/")
    public Stock getStockBySymbolNameId(@RequestParam(required = false) String symbol,
                                  @RequestParam(required = false) Integer id,
                                  @RequestParam(required = false) String name){
        if( id != null){
            return stockService.getStockById(id);
        }
        else if(symbol != null){
            return stockService.getStockBySymbol(symbol);
        }
        else{
            return stockService.getStockByName(name);
        }
    }

    @PutMapping("/update/{id}")
    public Stock updateStock(@PathVariable int id, @RequestBody Stock stock){
        return stockService.updateStock(id, stock);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteStockById(@PathVariable int id){
        stockService.deleteStock(id);
    }
}




















