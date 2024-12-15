package com.learnbydoing.tradingapp.controller;

import com.learnbydoing.tradingapp.enums.TransactionType;
import com.learnbydoing.tradingapp.service.VirtualCurrencyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/virtual-currency")
public class VirtualCurrencyController {

    private final VirtualCurrencyService virtualCurrencyService;

    public VirtualCurrencyController(VirtualCurrencyService virtualCurrencyService) {
        this.virtualCurrencyService = virtualCurrencyService;
    }

    // Get the user's balance
    @GetMapping("/balance/{userId}")
    public double getBalance(@PathVariable Integer userId) {
        return virtualCurrencyService.getBalance(userId);
    }

    // Update the balance (Credit/Debit)
    @PostMapping("/update/{userId}")
    public ResponseEntity<String> updateBalance(@PathVariable Integer userId, @RequestParam double amount, @RequestParam TransactionType transactionType) {
        virtualCurrencyService.updateBalance(userId, amount, transactionType);
        return ResponseEntity.ok("Balance updated successfully");
    }
}
