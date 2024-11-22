package com.learnbydoing.tradingapp.service;
import com.learnbydoing.tradingapp.entity.Portfolio;
import com.learnbydoing.tradingapp.entity.Stock;
import com.learnbydoing.tradingapp.entity.User;
import com.learnbydoing.tradingapp.enums.TransactionType;
import com.learnbydoing.tradingapp.repository.PortfolioRepository;
import com.learnbydoing.tradingapp.repository.StockRepository;
import com.learnbydoing.tradingapp.repository.TransactionRepository;
import com.learnbydoing.tradingapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class PortfolioService {

    @Autowired
    private PortfolioRepository portfolioRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void updatePortfolio(Integer userId, Integer stockId, Integer quantity, BigDecimal price, TransactionType transactionType) {
        // Fetch the portfolio record for the user and stock
        User user = userRepository.findById(userId).orElseThrow(()->new NoSuchElementException("User not found with id: "+userId));
        Stock stock = stockRepository.findById(stockId).orElseThrow(()-> new NoSuchElementException("Stock not found with id: "+stockId));
        Portfolio portfolio = portfolioRepository.findByUserIdAndStockId(userId, stockId)
                .orElseGet(() -> createNewPortfolio(user, stock));

        // Update portfolio based on transaction type
        if (transactionType == TransactionType.BUY) {
            updateForBuyTransaction(portfolio, quantity, price);
        } else if (transactionType == TransactionType.SELL) {
            updateForSellTransaction(portfolio, quantity, price);
        }

        // Save updated portfolio record
        portfolioRepository.save(portfolio);
    }

    private Portfolio createNewPortfolio(User user, Stock stock) {
        Portfolio portfolio = new Portfolio();
        portfolio.setUser(user);
        portfolio.setStock(stock);
        portfolio.setTotalQuantity(0);
        portfolio.setAveragePrice(BigDecimal.ZERO);
        portfolio.setProfitLoss(BigDecimal.ZERO);
        return portfolio;
    }

    private void updateForBuyTransaction(Portfolio portfolio, Integer quantity, BigDecimal price) {
        // Update quantity and calculate average price for buy transaction
        BigDecimal totalCost = portfolio.getAveragePrice().multiply(BigDecimal.valueOf(portfolio.getTotalQuantity()))
                .add(price.multiply(BigDecimal.valueOf(quantity)));
        portfolio.setTotalQuantity(portfolio.getTotalQuantity() + quantity);
        portfolio.setAveragePrice(totalCost.divide(BigDecimal.valueOf(portfolio.getTotalQuantity()), BigDecimal.ROUND_HALF_UP));
    }

    private void updateForSellTransaction(Portfolio portfolio, Integer quantity, BigDecimal price) {
        if (portfolio.getTotalQuantity() < quantity) {
            throw new IllegalArgumentException("Insufficient stock quantity to sell");
        }

        // Update quantity and calculate profit/loss for sell transaction
        BigDecimal totalSale = price.multiply(BigDecimal.valueOf(quantity));
        BigDecimal costPrice = portfolio.getAveragePrice().multiply(BigDecimal.valueOf(quantity));
        BigDecimal profitLoss = totalSale.subtract(costPrice);
        portfolio.setTotalQuantity(portfolio.getTotalQuantity() - quantity);
        portfolio.setProfitLoss(portfolio.getProfitLoss().add(profitLoss));
    }

    public Portfolio getPortfolio(Integer userId, Integer stockId) {
        return portfolioRepository.findByUserIdAndStockId(userId, stockId)
                .orElseThrow(() -> new RuntimeException("Portfolio not found"));
    }
}
