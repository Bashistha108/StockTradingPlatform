package com.learnbydoing.tradingapp.repository;

import com.learnbydoing.tradingapp.entity.VirtualCurrencyBalance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VirtualCurrencyBalanceRepository extends JpaRepository<VirtualCurrencyBalance, Integer> {
    Optional<VirtualCurrencyBalance> findByUserId(Integer userId);
}