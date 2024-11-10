package com.learnbydoing.tradingapp.repository;

import com.learnbydoing.tradingapp.entity.StockPriceHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockPriceHistoryRepository extends JpaRepository<StockPriceHistory, Integer> {

    // Custom method to find stock price history by stockId
    List<StockPriceHistory> findByStockId(int stockId);
}
