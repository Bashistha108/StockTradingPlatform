
package com.learnbydoing.tradingapp.service;

import com.learnbydoing.tradingapp.entity.User;
import com.learnbydoing.tradingapp.entity.VirtualCurrencyTransaction;
import com.learnbydoing.tradingapp.enums.TransactionType;
import com.learnbydoing.tradingapp.exceptionHandler.ResourceNotFoundException; // If you have a custom exception
import com.learnbydoing.tradingapp.repository.VirtualCurrencyTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class VirtualCurrencyTransactionService {

    private final VirtualCurrencyTransactionRepository transactionRepository;
    private final UserService userService;

    @Autowired
    public VirtualCurrencyTransactionService(VirtualCurrencyTransactionRepository transactionRepository, UserService userService) {
        this.transactionRepository = transactionRepository;
        this.userService = userService;
    }

    /**
     * Creates a new virtual currency transaction.
     *
     * @param userId the ID of the user
     * @param transactionTypeStr the type of transaction as a string (e.g., "CREDIT" or "DEBIT")
     * @param amount the transaction amount
     * @param transactionId an identifier for this transaction (optional use case)
     * @return the saved VirtualCurrencyTransaction
     */
    @Transactional
    public VirtualCurrencyTransaction createTransaction(Integer userId, String transactionTypeStr, double amount, Integer transactionId) {
        // Ensure the user exists
        User user = userService.getUserById(userId);
        if (user == null) {
            throw new ResourceNotFoundException("User not found with ID: " + userId);
        }

        // Validate and parse the TransactionType
        TransactionType transactionType;
        try {
            transactionType = TransactionType.valueOf(transactionTypeStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid transaction type: " + transactionTypeStr);
        }

        // Create and save the transaction
        VirtualCurrencyTransaction transaction = new VirtualCurrencyTransaction();
        transaction.setUser(user);
        transaction.setTransactionType(transactionType);
        transaction.setAmount(amount);
        transaction.setTransactionId(transactionId); // If this is meant to link to another system's ID

        return transactionRepository.save(transaction);
    }

    /**
     * Retrieves the list of transactions associated with a user.
     *
     * @param userId the ID of the user
     * @return list of VirtualCurrencyTransaction for that user
     */
    public List<VirtualCurrencyTransaction> getTransactionsByUserId(Integer userId) {
        return transactionRepository.findByUserUserId(userId);
    }
}
