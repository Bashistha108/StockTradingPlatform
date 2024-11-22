package com.learnbydoing.tradingapp.controller;

import com.learnbydoing.tradingapp.entity.Transaction;
import com.learnbydoing.tradingapp.enums.OrderType;
import com.learnbydoing.tradingapp.enums.TransactionType;
import com.learnbydoing.tradingapp.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/buy")
    public Transaction buyStock(@RequestParam Integer userId, @RequestParam Integer stockId,
                                @RequestParam Integer quantity, @RequestParam BigDecimal price,
                                @RequestParam OrderType orderType) {
        return transactionService.createTransaction(userId, stockId, TransactionType.BUY, quantity, price, orderType);
    }

    @PostMapping("/sell")
    public Transaction sellStock(@RequestParam Integer userId, @RequestParam Integer stockId,
                                 @RequestParam Integer quantity, @RequestParam BigDecimal price,
                                 @RequestParam OrderType orderType) {
        return transactionService.createTransaction(userId, stockId, TransactionType.SELL, quantity, price, orderType);
    }
}
