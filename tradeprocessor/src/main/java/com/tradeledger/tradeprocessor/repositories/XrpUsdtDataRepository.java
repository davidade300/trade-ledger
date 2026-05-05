package com.tradeledger.tradeprocessor.repositories;

import com.tradeledger.tradeprocessor.entities.XrpUsdtData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface XrpUsdtDataRepository extends JpaRepository<XrpUsdtData, Long> {
}
