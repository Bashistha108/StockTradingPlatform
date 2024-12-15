package com.learnbydoing.tradingapp.controller;

import com.learnbydoing.tradingapp.entity.LiveStockPrice;
import com.learnbydoing.tradingapp.entity.Stock;
import com.learnbydoing.tradingapp.entity.User;
import com.learnbydoing.tradingapp.enums.OrderType;
import com.learnbydoing.tradingapp.enums.TransactionType;
import com.learnbydoing.tradingapp.repository.UserRepository;
import com.learnbydoing.tradingapp.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private  UserRepository userRepository;
    @Autowired
    private FinnhubService finnhubService;
    @Autowired
    private TransactionService transactionService;

    @Autowired
    private PortfolioService portfolioService;

    @Autowired
    private StockService stockService;
    @Autowired
    private VirtualCurrencyBalanceService virtualCurrencyService;

    @PostMapping("/buy")
    public String buyStock(@RequestParam int userId,
                           @RequestParam int stockId,
                           @RequestParam double quantity,
                           @RequestParam double price) {
        try {
            // Create a BUY transaction
            System.out.println("Entering create transaction from CONtroller");
            transactionService.createTransaction(userId, stockId, TransactionType.BUY, quantity, price, OrderType.MARKET);
            System.out.println("Exiting create transaction from CONtroller");
            System.out.println("Entering update Portfoplio from CONtroller");

            // Update user's portfolio
            portfolioService.updatePortfolio(userId, stockId, quantity, price, TransactionType.BUY);
            System.out.println("Exiting update portfolio from CONtroller");

            // Debit user's virtual currency balance
            double totalCost = quantity * price;
            virtualCurrencyService.updateBalance(userId, totalCost, TransactionType.DEBIT);
            System.out.println("Stock bought successfully");
            return "redirect:/portfolio/";
        } catch (Exception e) {
            return "Error occurred during buy transaction: " + e.getMessage();
        }
    }

    @GetMapping("/order")
    public String showFormForOrder(Model model, @RequestParam("stockId") int stockId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findByEmail(email);
        int userId = user.getUserId();
        Stock stock = stockService.getStockById(stockId);
        String symbol = stockService.getStockById(stockId).getStockSymbol();
        LiveStockPrice liveStockPrice = finnhubService.getLiveStockPrice(symbol);
        double currentPrice = liveStockPrice.getCurrentPrice();
        model.addAttribute("currentPrice", currentPrice);
        model.addAttribute("userId", userId);
        model.addAttribute("stockId", stockId);
        model.addAttribute("stock", stock);
        return "stock/order-form";
    }

    @PostMapping("/sell")
    public String sellStock(@RequestParam int userId,
                            @RequestParam int stockId,
                            @RequestParam double quantity,
                            @RequestParam double price) {
        try {
            // Create a SELL transaction
            transactionService.createTransaction(userId, stockId, TransactionType.SELL, quantity, price, OrderType.MARKET);
            // Update user's portfolio
            portfolioService.updatePortfolio(userId, stockId, quantity, price, TransactionType.SELL);

            System.out.println("-----------------------------");
            System.out.println("-----------------------------");
            System.out.println("EXIT UPDATE PORTFOLIO FROM CONTROLLER");
            System.out.println("-----------------------------");
            System.out.println("-----------------------------");

            // Credit user's virtual currency balance
            double totalSale = quantity * price;

            virtualCurrencyService.updateBalance(userId, totalSale, TransactionType.CREDIT);
            System.out.println("-----------------------------");
            System.out.println("-----------------------------");
            System.out.println("ENTER 1");
            System.out.println("-----------------------------");
            System.out.println("-----------------------------");

            return "Stock sold successfully!";
        } catch (Exception e) {
            return "Error occurred during sell transaction: " + e.getMessage();
        }
    }

    // Bóth /buy and /sell combined, reduce redundancy
    @PostMapping("/buy-sell")
    public String handleTransaction(@RequestParam int userId,
                                    @RequestParam int stockId,
                                    @RequestParam(required = false) Double quantity,
                                    @RequestParam(required = false) Double amount,
                                    @RequestParam TransactionType transactionType,
                                    @RequestParam double currentPrice) {
        try {
            if (currentPrice <= 0) {
                throw new IllegalArgumentException("Stock price is invalid or not available.");
            }

            double total = 0.0;
            if (quantity != null && quantity > 0) {
                total = quantity * currentPrice;  // Calculate total for quantity
            } else if (amount != null && amount > 0) {
                total = amount;  // Use amount directly
            }

            System.out.println("_---------------------------------");
            System.out.println("Received stockId: " + stockId);
            System.out.println("Received quantity: " + quantity);
            System.out.println("Received amount: " + amount);

            if (quantity != null && amount != null) {
                // If both are filled, you might want to prioritize one over the other or calculate the total
                System.out.println("Both quantity and amount are provided. Prioritizing quantity.");
            }

            // Create the transaction (either BUY or SELL)
            System.out.println("Entering create transaction from Controller");
            transactionService.createTransaction(userId, stockId, transactionType, quantity, amount, OrderType.MARKET);
            System.out.println("Exiting create transaction from Controller");

            // Update the user's portfolio
            System.out.println("Entering update Portfolio from Controller");
            portfolioService.updatePortfolio(userId, stockId, quantity, amount, transactionType);
            System.out.println("Exiting update Portfolio from Controller");



            // Debit or Credit the user's virtual currency balance based on transaction type
            if (transactionType == TransactionType.BUY) {
                virtualCurrencyService.updateBalance(userId, total, TransactionType.DEBIT);
                System.out.println("Stock bought successfully");
            } else if (transactionType == TransactionType.SELL) {
                virtualCurrencyService.updateBalance(userId, total, TransactionType.CREDIT);
                System.out.println("Stock sold successfully");
            }

            return "redirect:/";  // Redirect after successful transaction
        } catch (Exception e) {
            return "Error occurred during transaction: " + e.getMessage();
        }
    }

    @GetMapping("/history/{userId}")
    public Object getTransactionHistory(@PathVariable int userId) {
        try {
            return transactionService.getTransactionHistory(userId);
        } catch (Exception e) {
            return "Error retrieving transaction history: " + e.getMessage();
        }
    }
}
