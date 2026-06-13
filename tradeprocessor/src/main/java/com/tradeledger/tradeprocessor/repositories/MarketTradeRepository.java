package com.tradeledger.tradeprocessor.repositories;

import com.tradeledger.tradeprocessor.entities.MarketTrade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MarketTradeRepository extends JpaRepository<MarketTrade, Long> {
}
