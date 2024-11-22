package com.learnbydoing.tradingapp.service;

import com.learnbydoing.tradingapp.entity.Stock;
import com.learnbydoing.tradingapp.entity.Transaction;
import com.learnbydoing.tradingapp.entity.User;
import com.learnbydoing.tradingapp.enums.OrderStatus;
import com.learnbydoing.tradingapp.enums.OrderType;
import com.learnbydoing.tradingapp.enums.TransactionType;
import com.learnbydoing.tradingapp.repository.StockRepository;
import com.learnbydoing.tradingapp.repository.TransactionRepository;
import com.learnbydoing.tradingapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public Transaction createTransaction(Integer userId, Integer stockId, TransactionType transactionType,
                                         Integer quantity, BigDecimal price, OrderType orderType) {
        // Ensure the user and stock exist
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Stock stock = stockRepository.findById(stockId).orElseThrow(() -> new RuntimeException("Stock not found"));

        // Create new transaction
        Transaction transaction = new Transaction();
        transaction.setUser(user);
        transaction.setStock(stock);
        transaction.setTransactionType(transactionType);
        transaction.setQuantity(quantity);
        transaction.setPrice(price);
        transaction.setOrderType(orderType);
        transaction.setOrderStatus(OrderStatus.PENDING); // Set status as pending initially

        // Save the transaction
        return transactionRepository.save(transaction);
    }


}
