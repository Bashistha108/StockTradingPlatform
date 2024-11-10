package com.learnbydoing.tradingapp.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "live_stock_price")
public class LiveStockPrice {

    @Id
    @Column(name = "stock_id")
    private int stockId;

    @Column(name = "current_price")
    private double currentPrice;

    @Column(name = "last_updated")
    private String lastUpdated;

    @ManyToOne
    @JoinColumn(name = "stock_id", insertable = false, updatable = false)
    private Stock stock;

    public LiveStockPrice() {
    }

    public LiveStockPrice(int stockId, double currentPrice, String lastUpdated, Stock stock) {
        this.stockId = stockId;
        this.currentPrice = currentPrice;
        this.lastUpdated = lastUpdated;
        this.stock = stock;
    }

    public int getStockId() {
        return stockId;
    }

    public void setStockId(int stockId) {
        this.stockId = stockId;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        return "LiveStockPrice{" +
                "stockId=" + stockId +
                ", currentPrice=" + currentPrice +
                ", lastUpdated='" + lastUpdated + '\'' +
                ", stock=" + stock +
                '}';
    }
}
