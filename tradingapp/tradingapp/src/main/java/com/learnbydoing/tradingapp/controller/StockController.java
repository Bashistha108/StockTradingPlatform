package com.learnbydoing.tradingapp.controller;

import com.learnbydoing.tradingapp.entity.Stock;
import com.learnbydoing.tradingapp.entity.User;
import com.learnbydoing.tradingapp.entity.UserType;
import com.learnbydoing.tradingapp.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/stocks")
public class StockController {

    private final StockService stockService;

    @Autowired
    public StockController(StockService stockService){
        this.stockService = stockService;
    }

    @GetMapping("/add-form")
    public String showFormForAdd(Model model){
        Stock stock = new Stock();
        model.addAttribute("stock",stock);
        return "/stock/stock-add-form";
    }

    @GetMapping("/update-form/{stockId}")
    public String showFormForUpdate(@PathVariable("stockId") int id, Model model){
        Stock stock = stockService.getStockById(id);
        model.addAttribute("stock", stock);
        return "stock/stock-add-form";
    }

    @PostMapping("/add")
    public String addStock(@ModelAttribute Stock stock){
            System.out.println(stock.getId());
            String symbol = stock.getStockSymbol().toUpperCase();
            String name = stock.getStockName().toUpperCase();
            stock.setStockSymbol(symbol);
            stock.setStockName(name);
            stockService.saveAndUpdateStock(stock);
            return "redirect:/stocks/manage-stocks";

    }
/*
    @PostMapping("/update")
    public String showFormForUpdate(@RequestBody Stock stock){
        stockService.saveAndUpdateStock(stock);
        return "stock/stock-add-form";
    }
 */

/*
    @PutMapping("/update/{id}")
    public String updateStock(@PathVariable int id){
         stockService.updateStock(id, stock);
         return "redirect:/stocks/manage-stocks";
    }
*/

    @GetMapping("/manage-stocks")
    public String manageStocks(Model model){
        List<Stock> stocks = stockService.getAllStocks();
        model.addAttribute("stocks", stocks);
        return "stock/stock-manage";
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


    @GetMapping("/delete-stock/{id}")
    public String deleteStockById(@PathVariable int id){
        stockService.deleteStock(id);
        return "redirect:/stocks/manage-stocks";
    }
}




















