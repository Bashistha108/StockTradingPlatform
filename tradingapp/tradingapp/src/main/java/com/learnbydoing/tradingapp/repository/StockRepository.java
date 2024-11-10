package com.learnbydoing.tradingapp.repository;

import com.learnbydoing.tradingapp.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepository extends JpaRepository<Stock, Integer> {


    // If in the Stock Entity class a field declared as stockName, must be here find by StockName. If declared in Stock entity name, then here findByName
    Stock findByStockSymbol(String symbol);
    Stock findByStockName(String name);



}
