package com.learnbydoing.tradingapp.service;

import com.learnbydoing.tradingapp.entity.Stock;
import com.learnbydoing.tradingapp.entity.Transaction;
import com.learnbydoing.tradingapp.entity.User;
import com.learnbydoing.tradingapp.enums.OrderStatus;
import com.learnbydoing.tradingapp.enums.OrderType;
import com.learnbydoing.tradingapp.enums.TransactionType;
import com.learnbydoing.tradingapp.exceptionHandler.ResourceNotFoundException;
import com.learnbydoing.tradingapp.repository.StockRepository;
import com.learnbydoing.tradingapp.repository.TransactionRepository;
import com.learnbydoing.tradingapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final StockRepository stockRepository;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository, StockRepository stockRepository, UserRepository userRepository){
        this.transactionRepository = transactionRepository;
        this.stockRepository = stockRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Transaction createTransaction(Integer userId, Integer stockId, TransactionType transactionType,
                                         Double quantity, Double price, OrderType orderType) {

        System.out.println("ENTERING CREATE TRANSACTION..........");

        User user = userRepository.findByUserId(userId);
        Stock stock = stockRepository.findById(stockId).orElseThrow(()->new ResourceNotFoundException("Stock not found with id: "+stockId));

        if(quantity==null || quantity <= 0 || price <= 0){
            throw new IllegalArgumentException("Cannot be like given");
        }

        Transaction transaction = new Transaction();
        transaction.setUser(user);
        transaction.setStock(stock);
        transaction.setTransactionType(transactionType);
        transaction.setQuantity(quantity);
        transaction.setPrice(price);
        transaction.setOrderType(orderType);
        transaction.setOrderStatus(OrderStatus.PENDING);  // You can update status later if needed
        System.out.println("EXITING CREATE TRANSACTION..............");
        System.out.println(transaction.toString());
        return transactionRepository.save(transaction);
    }

    public List<Transaction> getTransactionHistory(int userId) {
        return transactionRepository.findByUser_UserId(userId);
    }
}































