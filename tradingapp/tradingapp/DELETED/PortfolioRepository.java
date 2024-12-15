package com.learnbydoing.tradingapp.repository;

import com.learnbydoing.tradingapp.entity.Portfolio;
import com.learnbydoing.tradingapp.entity.PortfolioId;
import com.learnbydoing.tradingapp.entity.Stock;
import com.learnbydoing.tradingapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Repository
public interface PortfolioRepository extends JpaRepository<Portfolio, PortfolioId> {
    Portfolio findByUserAndStock(User user, Stock stock);
    Optional<Portfolio> findById_UserIdAndId_StockId(int userId, int stockId);
    Optional<Portfolio> findByUserUserId(int userId);

}
