package com.learnbydoing.tradingapp.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "stocks")
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stock_id")
    private int id;


    @Column(name = "stock_name")
    private String stockName;

    @Column(name = "stock_symbol")
    private String stockSymbol;

    public Stock() {
    }

    public Stock(String stockName, String stockSymbol) {
        this.stockName = stockName;
        this.stockSymbol = stockSymbol;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public String getStockSymbol() {
        return stockSymbol;
    }

    public void setStockSymbol(String stockSymbol) {
        this.stockSymbol = stockSymbol;
    }

    @Override
    public String toString() {
        return "Stock{" +
                "id=" + id +
                ", stockName='" + stockName + '\'' +
                ", stockSymbol='" + stockSymbol + '\'' +
                '}';
    }
}
