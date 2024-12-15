package com.learnbydoing.tradingapp.repository;

import com.learnbydoing.tradingapp.entity.VirtualCurrencyTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VirtualCurrencyTransactionRepository extends JpaRepository<VirtualCurrencyTransaction, Integer> {
    List<VirtualCurrencyTransaction> findByUser_UserId(Integer userId);
}