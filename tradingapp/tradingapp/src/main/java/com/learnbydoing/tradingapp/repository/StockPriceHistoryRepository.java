package com.learnbydoing.tradingapp.repository;

import com.learnbydoing.tradingapp.entity.StockPriceHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockPriceHistoryRepository extends JpaRepository<StockPriceHistory, Integer> {
}
