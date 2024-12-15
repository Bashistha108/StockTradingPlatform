package com.learnbydoing.tradingapp.service;
import com.learnbydoing.tradingapp.entity.Portfolio;
import com.learnbydoing.tradingapp.entity.PortfolioId;
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

import java.util.NoSuchElementException;

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

    // Fetch portfolio for a specific user
    public Portfolio getPortfolio(int userId) {
        return portfolioRepository.findByUserUserId(userId).orElseThrow(()-> new NoSuchElementException("No porffolio"));
    }

    @Transactional
    public void updatePortfolio(Integer userId, Integer stockId, double quantity, double price, TransactionType transactionType) {

        System.out.println("Entering PortfolioService.updatePortfolio");

        // Fetch the portfolio record for the user and stock
        User user = userRepository.findById(userId).orElseThrow(()->new NoSuchElementException("User not found with id: "+userId));
        Stock stock = stockRepository.findById(stockId).orElseThrow(()-> new NoSuchElementException("Stock not found with id: "+stockId));
        Portfolio portfolio = portfolioRepository.findById_UserIdAndId_StockId(userId, stockId)
                .orElseGet(() -> createNewPortfolio(user, stock));

        // Update portfolio based on transaction type
        if (transactionType == TransactionType.BUY) {
            updateForBuyTransaction(portfolio, quantity, price);
        } else if (transactionType == TransactionType.SELL) {
            updateForSellTransaction(portfolio, quantity, price);
        }

        System.out.println("Exiting PortfolioService.updatePortfolio");

        // Save updated portfolio record
        portfolioRepository.save(portfolio);
    }


    private Portfolio createNewPortfolio(User user, Stock stock) {
        PortfolioId portfolioId = new PortfolioId(user.getUserId(), stock.getId());
        Portfolio portfolio = new Portfolio();
        portfolio.setId(portfolioId); // setting the composite key
        portfolio.setUser(user);
        portfolio.setStock(stock);
        portfolio.setTotalQuantity(0.0);
        portfolio.setAveragePrice(0.0);
        portfolio.setProfitLoss(0.0);
        return portfolio;
    }


    private void updateForBuyTransaction(Portfolio portfolio, Double quantity, double price) {
        // Calculate total cost for the new purchase (price * quantity)
        double totalCost = price * quantity;

        // Update total quantity and calculate new average price
        double newTotalQuantity = portfolio.getTotalQuantity() + quantity;
        portfolio.setAveragePrice(((portfolio.getAveragePrice() * portfolio.getTotalQuantity()) + totalCost) / newTotalQuantity);
        portfolio.setTotalQuantity(newTotalQuantity);
    }

    private void updateForSellTransaction(Portfolio portfolio, Double quantity, double price) {
        if (portfolio.getTotalQuantity() < quantity) {
            throw new IllegalArgumentException("Insufficient stock quantity to sell");
        }

        // Update quantity and calculate profit/loss for sell transaction
        double totalSale = price * quantity;
        double costPrice = portfolio.getAveragePrice() * quantity;
        double profitLoss = totalSale - costPrice;
        portfolio.setTotalQuantity(portfolio.getTotalQuantity() - quantity);
        portfolio.setProfitLoss(portfolio.getProfitLoss() + profitLoss);
    }

    public Portfolio getPortfolio(Integer userId, Integer stockId) {
        return portfolioRepository.findById_UserIdAndId_StockId(userId, stockId)
                .orElseThrow(() -> new RuntimeException("Portfolio not found"));
    }
}
