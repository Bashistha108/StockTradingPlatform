package com.learnbydoing.tradingapp.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "stocks")
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stock_id")
    private Integer id;


    @Column(name = "stock_name")
    private String stockName;

    @Column(name = "stock_symbol")
    private String stockSymbol;


    // Bidirectional mapping to StockPriceHistory
    @OneToMany(mappedBy = "stock", cascade = CascadeType.ALL)
    private List<StockPriceHistory> priceHistory;

    public Stock() {
    }

    public Stock(String stockName, String stockSymbol) {
        this.stockName = stockName;
        this.stockSymbol = stockSymbol;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public List<StockPriceHistory> getPriceHistory() {
        return priceHistory;
    }

    public void setPriceHistory(List<StockPriceHistory> priceHistory) {
        this.priceHistory = priceHistory;
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
