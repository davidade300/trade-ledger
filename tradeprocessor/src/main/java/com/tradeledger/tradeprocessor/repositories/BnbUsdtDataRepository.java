package com.tradeledger.tradeprocessor.repositories;

import com.tradeledger.tradeprocessor.entities.BnbUsdtData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BnbUsdtDataRepository extends JpaRepository<BnbUsdtData, Long> {
}
