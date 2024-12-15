package com.learnbydoing.tradingapp.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

@IdClass(PortfolioId.class)
@Entity
@Table(name = "portfolio")
public class Portfolio{


    /**
     * USE STPCK SYMBOL INSTEAD OF ID TO AVOID USING PORTFOLIO ID
     */
    @Id
    @Column(name = "user_id")
    private Integer userId;

    @Id
    @Column(name = "stock_id")
    private Integer stockId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "stock_id", insertable = false, updatable = false)
    private Stock stock;

    @Column(name = "total_quantity", nullable = false)
    private double totalQuantity = 0.0;

    @Column(name = "average_price")
    private double averagePrice;

    @Column(name = "profit_loss")
    private double profitLoss;

    public Portfolio() {
    }

    // Constructor
    public Portfolio(User user, Stock stock, double totalQuantity) {
        this.user = user;
        this.userId = user.getUserId();
        this.stock = stock;
        this.stockId = stock.getId();
        this.totalQuantity = totalQuantity;
    }

    public Portfolio(int userId, int stockId, double quantity){
        this.userId = userId;
        this.stockId = stockId;
        this.totalQuantity = quantity;
    }

    public double getProfitLoss() {
        return profitLoss;
    }

    public void setProfitLoss(double profitLoss) {
        this.profitLoss = profitLoss;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

    public double getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(double totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public double getAveragePrice() {
        return averagePrice;
    }

    public void setAveragePrice(double averagePrice) {
        this.averagePrice = averagePrice;
    }

    // Add explicit getters/setters for userId and stockId
    public Integer getUserId() {
        return this.userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getStockId() {
        return this.stockId;
    }

    public void setStockId(Integer stockId) {
        this.stockId = stockId;
    }

    @Override
    public String toString() {
        return "Portfolio{" +
                "userId=" + userId +
                ", stockId=" + stockId +
                ", totalQuantity=" + totalQuantity +
                ", averagePrice=" + averagePrice +
                ", profitLoss=" + profitLoss +
                '}';
    }
}