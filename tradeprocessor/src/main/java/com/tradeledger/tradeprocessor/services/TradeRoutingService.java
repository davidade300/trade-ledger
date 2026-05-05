package com.tradeledger.tradeprocessor.services;

import com.tradeledger.tradeprocessor.entities.TradeData;
import com.tradeledger.tradeprocessor.repositories.BnbUsdtDataRepository;
import com.tradeledger.tradeprocessor.repositories.BtcUsdtDataRepository;
import com.tradeledger.tradeprocessor.repositories.XrpUsdtDataRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TradeRoutingService {

    private final BtcUsdtDataRepository btcRepo;
    private final XrpUsdtDataRepository xrpRepo;
    private final BnbUsdtDataRepository bnbRepo;

    public TradeRoutingService(BtcUsdtDataRepository btcRepo, XrpUsdtDataRepository xrpRepo, BnbUsdtDataRepository bnbRepo) {
        this.btcRepo = btcRepo;
        this.xrpRepo = xrpRepo;
        this.bnbRepo = bnbRepo;
    }

    public void route(TradeData trade) {
        switch (trade.getSymbol()) {

        }
    }
}
