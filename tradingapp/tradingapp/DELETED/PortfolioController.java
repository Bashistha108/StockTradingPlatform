package com.learnbydoing.tradingapp.controller;

import com.learnbydoing.tradingapp.entity.Portfolio;
import com.learnbydoing.tradingapp.enums.TransactionType;
import com.learnbydoing.tradingapp.service.PortfolioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/portfolio")
public class PortfolioController {


    private final PortfolioService portfolioService;

    public PortfolioController(PortfolioService portfolioService){
        this.portfolioService = portfolioService;
    }

    // Get portfolio for a user
    @GetMapping("/{userId}")
    public Portfolio getPortfolio(@PathVariable int userId) {
        return portfolioService.getPortfolio(userId);
    }

    // Update portfolio after a transaction
    @PostMapping("/update")
    public String updatePortfolio(@RequestParam int userId, @RequestParam int stockId,
                                  @RequestParam double quantity, @RequestParam double price,
                                  @RequestParam TransactionType transactionType) {
        try {
            portfolioService.updatePortfolio(userId, stockId, quantity, price, transactionType);
            return "Portfolio updated successfully!";
        } catch (Exception e) {
            return "Error updating portfolio: " + e.getMessage();
        }
    }
}