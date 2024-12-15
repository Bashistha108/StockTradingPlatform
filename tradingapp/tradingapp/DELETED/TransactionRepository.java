package com.learnbydoing.tradingapp.repository;

import com.learnbydoing.tradingapp.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    // You can add custom queries here if needed, for example:
    List<Transaction> findByUser_UserId(Integer userId);
}