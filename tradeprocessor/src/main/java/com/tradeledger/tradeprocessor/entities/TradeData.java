package com.tradeledger.tradeprocessor.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.Instant;

@MappedSuperclass
public abstract class TradeData {
    // TODO: se funcionar, incluir definicao (nome, etc) das colunas
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String type;
    @Column
    private Instant eventTime;
    @Column
    private String symbol;
    @Column
    private Long tradeId;
    @Column
    private BigDecimal price;
    @Column
    private BigDecimal quantity;
    @Column
    private Instant tradeTime;
    @Column
    private Boolean marketMaker;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Instant getEventTime() {
        return eventTime;
    }

    public void setEventTime(Instant eventTime) {
        this.eventTime = eventTime;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Long getTradeId() {
        return tradeId;
    }

    public void setTradeId(Long tradeId) {
        this.tradeId = tradeId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public Instant getTradeTime() {
        return tradeTime;
    }

    public void setTradeTime(Instant tradeTime) {
        this.tradeTime = tradeTime;
    }

    public Boolean getMarketMaker() {
        return marketMaker;
    }

    public void setMarketMaker(Boolean marketMaker) {
        this.marketMaker = marketMaker;
    }
}
