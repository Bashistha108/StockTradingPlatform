package com.learnbydoing.tradingapp.entity;

import jakarta.persistence.*;

import java.sql.Date;

@Entity
@Table(name = "stock_price_history")
public class StockPriceHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "stock_id", referencedColumnName = "stock_id", insertable = false, updatable = false)
    private Stock stock; // The Stock entity is referenced here

    @Column(name = "stock_id")
    private int stockId; // This is the foreign key column for the Stock entity. No need for @ManyToOne here.

    @Column(name = "date")
    private Date date;

    @Column(name = "open_price")
    private double openPrice;

    @Column(name = "close_price")
    private double closePrice;

    @Column(name = "high_price")
    private double highPrice;

    @Column(name = "low_price")
    private double lowPrice;

    @Column(name = "volume")
    private long volume;

    // Getters and setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

    public int getStockId() {
        return stockId;
    }

    public void setStockId(int stockId) {
        this.stockId = stockId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getOpenPrice() {
        return openPrice;
    }

    public void setOpenPrice(double openPrice) {
        this.openPrice = openPrice;
    }

    public double getClosePrice() {
        return closePrice;
    }

    public void setClosePrice(double closePrice) {
        this.closePrice = closePrice;
    }

    public double getHighPrice() {
        return highPrice;
    }

    public void setHighPrice(double highPrice) {
        this.highPrice = highPrice;
    }

    public double getLowPrice() {
        return lowPrice;
    }

    public void setLowPrice(double lowPrice) {
        this.lowPrice = lowPrice;
    }

    public long getVolume() {
        return volume;
    }

    public void setVolume(long volume) {
        this.volume = volume;
    }

    @Override
    public String toString() {
        return "StockPriceHistory{" +
                "id=" + id +
                ", stockId=" + stockId +
                ", date=" + date +
                ", openPrice=" + openPrice +
                ", closePrice=" + closePrice +
                ", highPrice=" + highPrice +
                ", lowPrice=" + lowPrice +
                ", volume=" + volume +
                '}';
    }
}
