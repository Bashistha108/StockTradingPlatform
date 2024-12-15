package com.learnbydoing.tradingapp.repository;

import com.learnbydoing.tradingapp.entity.VirtualCurrencyTransaction;
import com.learnbydoing.tradingapp.enums.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VirtualCurrencyTransactionRepository extends JpaRepository<VirtualCurrencyTransaction, Integer> {
    // Find all transactions for a specific user
    List<VirtualCurrencyTransaction> findByUserUserId(Integer userId);

    // Find transactions by type (CREDIT or DEBIT) for a user
    List<VirtualCurrencyTransaction> findByUserUserIdAndTransactionType(Integer userId, TransactionType transactionType);
}
