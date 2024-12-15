package com.learnbydoing.tradingapp.controller;



import com.learnbydoing.tradingapp.entity.LiveStockPrice;
import com.learnbydoing.tradingapp.entity.Portfolio;
import com.learnbydoing.tradingapp.entity.Transaction;
import com.learnbydoing.tradingapp.entity.User;
import com.learnbydoing.tradingapp.enums.OrderType;
import com.learnbydoing.tradingapp.enums.TransactionType;
import com.learnbydoing.tradingapp.repository.PortfolioRepository;
import com.learnbydoing.tradingapp.repository.StockRepository;
import com.learnbydoing.tradingapp.repository.UserRepository;
import com.learnbydoing.tradingapp.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private PortfolioService portfolioService;

    @Autowired
    private LiveStockPriceServiceImpl liveStockPriceService;

    @Autowired
    private PortfolioRepository portfolioRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private VirtualCurrencyService virtualCurrencyService;



    // API for buying stocks
    @PostMapping("/buy")
    public String buyStock(@RequestParam int userId, @RequestParam int stockId,
                           @RequestParam(required = false) Double quantity, @RequestParam double price) {
        try {

            // Create a new transaction for buying
            Transaction transaction = transactionService.createTransaction(userId, stockId,TransactionType.BUY, quantity, price, OrderType.MARKET);

            // Update user's portfolio after transaction
            portfolioService.updatePortfolio(userId, stockId, quantity, price, TransactionType.BUY);

            // Update the user's virtual balance after the buy transaction
            virtualCurrencyService.updateBalance(userId, price, TransactionType.DEBIT);



            return "Stock bought successfully!";
        } catch (Exception e) {
            return "Error occurred while processing the buy transaction: " + e.getMessage();
        }
    }

    // API for selling stocks
    @PostMapping("/sell")
    public String sellStock(@RequestParam int userId, @RequestParam int stockId,
                            @RequestParam Double quantity, @RequestParam double price) {
        try {
            // Create a new transaction for selling
            Transaction transaction = transactionService.createTransaction(userId, stockId,TransactionType.SELL, (double) quantity, price, OrderType.MARKET);

            // Update user's portfolio after transaction
            portfolioService.updatePortfolio(userId, stockId, (double) quantity, price, TransactionType.SELL);

            // Update the user's virtual balance after the sell transaction
            virtualCurrencyService.updateBalance(userId, price*quantity, TransactionType.CREDIT);

            return "Stock sold successfully!";
        } catch (Exception e) {
            return "Error occurred while processing the sell transaction: " + e.getMessage();
        }
    }

    // Get transaction history for a user
    @GetMapping("/history/{userId}")
    public List<Transaction> getTransactionHistory(@PathVariable int userId) {
        return transactionService.getTransactionHistory(userId);
    }
}
