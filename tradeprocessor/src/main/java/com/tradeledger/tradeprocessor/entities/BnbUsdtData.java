package com.tradeledger.tradeprocessor.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "bnb_usdt")
public class BnbUsdtData extends TradeData {
    public BnbUsdtData() {
    }
}
