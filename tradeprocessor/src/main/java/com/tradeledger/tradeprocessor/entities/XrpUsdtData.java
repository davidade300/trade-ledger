package com.tradeledger.tradeprocessor.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "xrp_usdt")
public class XrpUsdtData extends TradeData {
    public XrpUsdtData() {
    }
}
