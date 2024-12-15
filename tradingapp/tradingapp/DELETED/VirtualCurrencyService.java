package com.learnbydoing.tradingapp.service;

import com.learnbydoing.tradingapp.entity.User;
import com.learnbydoing.tradingapp.entity.VirtualCurrencyBalance;
import com.learnbydoing.tradingapp.entity.VirtualCurrencyTransaction;
import com.learnbydoing.tradingapp.enums.TransactionType;
import com.learnbydoing.tradingapp.exceptionHandler.InsufficientFundsException;
import com.learnbydoing.tradingapp.exceptionHandler.ResourceNotFoundException;
import com.learnbydoing.tradingapp.repository.UserRepository;
import com.learnbydoing.tradingapp.repository.VirtualCurrencyBalanceRepository;
import com.learnbydoing.tradingapp.repository.VirtualCurrencyTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.NoSuchElementException;

@Service
public class VirtualCurrencyService {

    private final VirtualCurrencyBalanceRepository balanceRepository;
    private final VirtualCurrencyTransactionRepository transactionRepository;
    private final UserService userService;

    @Autowired
    public VirtualCurrencyService(VirtualCurrencyBalanceRepository balanceRepository, VirtualCurrencyTransactionRepository transactionRepository, UserService userService) {
        this.balanceRepository = balanceRepository;
        this.transactionRepository = transactionRepository;
        this.userService = userService;
        System.out.println("UserService injected: " + (this.userService != null));
    }


    // Get current balance
    public double getBalance(Integer userId) {
        VirtualCurrencyBalance balance = balanceRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException ("User balance not found"));
        return balance.getBalance();
    }

    // Update balance (credit or debit)
    public void updateBalance(Integer userId, double amount, TransactionType transactionType) {

        System.out.println("Entering VirtualCurrencyService.updateBalance");

        // Check if the user has an existing balance, otherwise set to 10,000
        VirtualCurrencyBalance balance = balanceRepository.findByUserId(userId)
                .orElseGet(() -> {
                    System.out.println("Balance not found for userId: " + userId + ". Creating default balance.");
                    return createDefaultBalance(userId);
                });
        System.out.println("Exiting VirtualCurrencyService.updateBalance 1");

        // If it's a debit transaction, ensure the user has enough funds
        if (transactionType == TransactionType.DEBIT && balance.getBalance() < amount) {
            throw new InsufficientFundsException("Insufficient funds to complete the transaction");
        }

        System.out.println("Exiting VirtualCurrencyService.updateBalance 2");

        // Update balance based on transaction type
        double newBalance = transactionType == TransactionType.CREDIT
                ? balance.getBalance() + amount
                : balance.getBalance() - amount;

        System.out.println("Exiting VirtualCurrencyService.updateBalance 3");

        balance.setBalance(newBalance);
        balanceRepository.save(balance);

        System.out.println("Exiting VirtualCurrencyService.updateBalance 4");

        // Record the transaction
        VirtualCurrencyTransaction transaction = new VirtualCurrencyTransaction();
        transaction.setUser(balance.getUser());
        transaction.setTransactionType(transactionType);
        transaction.setAmount(amount);
        System.out.println("Exiting VirtualCurrencyService.updateBalance 5");

        transactionRepository.save(transaction);
        System.out.println("Exiting VirtualCurrencyService.updateBalance 6");

    }

    // Creating a default balance of 10000 for user if not found

    private VirtualCurrencyBalance createDefaultBalance(Integer userId) {
        System.out.println("Entering create balance");
        System.out.println("User id: "+userId);
        // Retrieve the User object by userId
        try {
            // Retrieve the User object by userId
            User user = userService.getUserById(userId);

            // Log the user object if found
            if (user != null) {
                System.out.println("User retrieved: " + user);
            } else {
                System.out.println("User not found in the database.");
            }
        } catch (Exception e) {
            System.out.println("Error retrieving user: " + e.getMessage());
            e.printStackTrace(); // Log the full exception stack trace for more context
        }


        VirtualCurrencyBalance defaultBalance = new VirtualCurrencyBalance();
        defaultBalance.setUserId(userId); // You may need to fetch the User object based on the userId.
        defaultBalance.setBalance(10000.0); // Default balance set to 10,000
        System.out.println("Exiting create balance");

        return balanceRepository.save(defaultBalance);
    }
}

































