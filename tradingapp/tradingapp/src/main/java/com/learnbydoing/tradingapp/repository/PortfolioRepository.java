package com.learnbydoing.tradingapp.repository;

import com.learnbydoing.tradingapp.entity.Portfolio;
import com.learnbydoing.tradingapp.entity.PortfolioId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PortfolioRepository extends JpaRepository<Portfolio, PortfolioId> {
    // Find all portfolios for a specific user
    List<Portfolio> findByUserUserId(Integer userId);

    // Find a specific portfolio entry by user and stock
    // findBy + userId(of user) and stock+stockId
    Portfolio findByUserUserIdAndStockId(Integer userId, Integer stockId);
    // Alternatively
    // @Query("SELECT p FROM Portfolio p WHERE p.user.userId = :userId AND p.stock.stockId = :stockId")
    // Portfolio findPortfolioByUserIdAndStockId(@Param("userId") Integer userId, @Param("stockId") Integer stockId);
}
