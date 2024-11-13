package com.learnbydoing.tradingapp.repository;

import com.learnbydoing.tradingapp.entity.StockPriceHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface StockPriceHistoryRepository extends JpaRepository<StockPriceHistory, Integer> {

    // Custom method to find stock price history by stockId
    List<StockPriceHistory> findByStockId(int stockId);
    // Add a custom query to check if the stock price history exists for a particular stock and date
    boolean existsByStockIdAndDate(int stockId, Date date);
}
