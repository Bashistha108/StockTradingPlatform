package com.learnbydoing.tradingapp.service;

import com.learnbydoing.tradingapp.entity.Portfolio;

import com.learnbydoing.tradingapp.entity.PortfolioId;
import com.learnbydoing.tradingapp.entity.Stock;
import com.learnbydoing.tradingapp.entity.User;
import com.learnbydoing.tradingapp.enums.TransactionType;
import com.learnbydoing.tradingapp.repository.PortfolioRepository;
import com.learnbydoing.tradingapp.repository.StockRepository;
import com.learnbydoing.tradingapp.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.sound.sampled.Port;
import java.sql.SQLOutput;
import java.util.NoSuchElementException;


@Service
public class PortfolioService {

    private final PortfolioRepository portfolioRepository;
    private final UserRepository userRepository;
    private final StockRepository stockRepository;


    private static final Logger logger = LoggerFactory.getLogger(PortfolioService.class);


    @Autowired
    public PortfolioService(PortfolioRepository portfolioRepository, UserRepository userRepository, StockRepository stockRepository){
        this.portfolioRepository = portfolioRepository;
        this.userRepository = userRepository;
        this.stockRepository = stockRepository;
    }

    public Portfolio findPortfolio(Integer userId, Integer stockId) {
        return portfolioRepository.findByUserUserIdAndStockId(userId, stockId);
    }
    @Transactional
    public void updatePortfolio(Integer userId, Integer stockId, double quantity, double price, TransactionType transactionType) {

        System.out.println("ENtering update Portfolio.............");
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found with id: " + userId));
        Stock stock = stockRepository.findById(stockId)
                .orElseThrow(() -> new NoSuchElementException("Stock not found with id: " + stockId));

        Portfolio portfolio = portfolioRepository.findByUserUserIdAndStockId(userId, stockId);
        if(portfolio == null){
            portfolio = new Portfolio();
            portfolio.setUser(user);
            portfolio.setUserId(userId);
            portfolio.setStock(stock);
            portfolio.setStockId(stockId);
            portfolio.setTotalQuantity(quantity);

            logger.info("Saving portfolio: " + portfolio);
            portfolioRepository.save(portfolio);
            logger.info("Portfolio saved successfully");
        }


        if (transactionType == TransactionType.BUY) {
            updateForBuyTransaction(portfolio, quantity, price);
        } else if (transactionType == TransactionType.SELL) {
            updateForSellTransaction(portfolio, quantity, price);
        }

        portfolioRepository.save(portfolio);
        System.out.println(portfolio.toString());
        System.out.println("EXITTTINGNGNGNGNG UPDATE PORTFOLIO................");



    }




    @Transactional
    public Portfolio savePortfolio(Portfolio portfolio) {
        return portfolioRepository.save(portfolio);
    }

    @Transactional
    public void addPortfolio(int userId, int stockId, double quantity) {
        System.out.println("-----------------");
        logger.info("Starting addPortfolio with userId: " + userId + ", stockId: " + stockId + ", quantity: " + quantity);

        try {
            User user = userRepository.findByUserId(userId);
            if (user == null) {
                logger.error("No user found with userId: {}", userId);
                throw new IllegalArgumentException("User not found with userId: " + userId);
            }
            logger.info("User fetched successfully: {}", user);
            Stock stock = stockRepository.findById(stockId).orElseThrow(() -> new IllegalArgumentException("Stock not found"));
            logger.info("Stock fetched: " + stock);

            Portfolio portfolio = new Portfolio();
            portfolio.setUser(user);
            portfolio.setUserId(userId);
            portfolio.setStock(stock);
            portfolio.setTotalQuantity(quantity);

            logger.info("Saving portfolio: " + portfolio);
            portfolioRepository.save(portfolio);
            logger.info("Portfolio saved successfully");
        } catch (Exception e) {
            logger.error("Error fetching user with userId: {}. Error: {}", userId, e.getMessage());
            throw e;
        }

    }

    @Transactional
    public void deletePortfolio(Portfolio portfolio) {
        portfolioRepository.delete(portfolio);
    }


    private void updateForBuyTransaction(Portfolio portfolio, double quantity, double price) {
        double totalCost = price * quantity;
        double newTotalQuantity = portfolio.getTotalQuantity() + quantity;
        double updatedAveragePrice = ((portfolio.getAveragePrice() * portfolio.getTotalQuantity()) + totalCost) / newTotalQuantity;

        portfolio.setTotalQuantity(newTotalQuantity);
        portfolio.setAveragePrice(updatedAveragePrice);
    }

    private void updateForSellTransaction(Portfolio portfolio, double quantity, double price) {
        if (portfolio.getTotalQuantity() < quantity) {
            throw new IllegalArgumentException("Insufficient stock quantity to sell");
        }

        double totalSale = price * quantity;
        double costPrice = portfolio.getAveragePrice() * quantity;
        double profitLoss = totalSale - costPrice;

        portfolio.setTotalQuantity(portfolio.getTotalQuantity() - quantity);
        portfolio.setProfitLoss(portfolio.getProfitLoss() + profitLoss);

        if (portfolio.getTotalQuantity() == 0) {
            portfolio.setAveragePrice(0.0);
        }
    }
}



































