package com.tradeledger.tradeprocessor.repositories;


import com.tradeledger.tradeprocessor.entities.BtcUsdtData;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BtcUsdtDataRepository extends JpaRepository<BtcUsdtData, Long> {
}
