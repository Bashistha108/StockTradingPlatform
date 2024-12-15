package com.learnbydoing.tradingapp.service;

import com.learnbydoing.tradingapp.entity.User;
import com.learnbydoing.tradingapp.entity.VirtualCurrencyBalance;
import com.learnbydoing.tradingapp.enums.TransactionType;
import com.learnbydoing.tradingapp.exceptionHandler.ResourceNotFoundException; // If you have this exception
import com.learnbydoing.tradingapp.repository.UserRepository;
import com.learnbydoing.tradingapp.repository.VirtualCurrencyBalanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service to manage virtual currency balances for users.
 */
@Service
public class VirtualCurrencyBalanceService {

    private final VirtualCurrencyBalanceRepository balanceRepository;
    private final UserRepository userRepository;

    @Autowired
    public VirtualCurrencyBalanceService(VirtualCurrencyBalanceRepository balanceRepository, UserRepository userRepository) {
        this.balanceRepository = balanceRepository;
        this.userRepository = userRepository;
    }

    /**
     * Finds a user's balance. If none exists, creates one with a default balance.
     *
     * @param userId the user's ID
     * @return the user's VirtualCurrencyBalance
     */
    public VirtualCurrencyBalance findBalanceByUserId(Integer userId) {
        return balanceRepository.findById(userId).orElseGet(() -> {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new ResourceNotFoundException("User not found with userId: " + userId));

            VirtualCurrencyBalance newBalance = new VirtualCurrencyBalance();
            // Set both user and userId
            newBalance.setUser(user);
            // userId will be automatically set from user due to @MapsId
            // But if needed, newBalance.setUserId(user.getUserId());

            newBalance.setBalance(10000.00); // Default balance
            return balanceRepository.save(newBalance);
        });
    }

    /**
     * Updates a user's balance.
     *
     * @param userId   the user's ID
     * @param amount   the amount to credit or debit
     * @return the updated VirtualCurrencyBalance
     */
    @Transactional
    public VirtualCurrencyBalance updateBalance(Integer userId, double amount, TransactionType transactionType) {
        VirtualCurrencyBalance balance = findBalanceByUserId(userId);
        boolean isCredit = transactionType == TransactionType.CREDIT;
        if (!isCredit && balance.getBalance() < amount) {
            // If this condition occurs, you can throw an InsufficientFundsException
            // For example:
            // throw new InsufficientFundsException("Insufficient funds to complete the transaction");
        }

        double updatedBalance = isCredit
                ? balance.getBalance() + amount
                : balance.getBalance() - amount;

        balance.setBalance(updatedBalance);
        return balanceRepository.save(balance);
    }
}
