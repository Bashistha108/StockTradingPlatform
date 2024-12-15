package com.learnbydoing.tradingapp.repository;

import com.learnbydoing.tradingapp.entity.VirtualCurrencyBalance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VirtualCurrencyBalanceRepository extends JpaRepository<VirtualCurrencyBalance, Integer> {
    // Optional: Add custom queries if needed
    VirtualCurrencyBalance findByUserUserId(Integer userId);
}
