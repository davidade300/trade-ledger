package com.tradeledger.tradeprocessor.services;

import com.tradeledger.tradeprocessor.Subscriber.representation.TradeDataRepresentation;
import com.tradeledger.tradeprocessor.entities.BnbUsdtData;
import com.tradeledger.tradeprocessor.entities.BtcUsdtData;
import com.tradeledger.tradeprocessor.entities.XrpUsdtData;
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

    public void route(TradeDataRepresentation representation) {
        switch (representation.symbol()) {
            case "BNBUSDT" -> {
                bnbRepo.save(representationToBnbData(representation));
                log.info("new {} trade registered", representation.symbol());
            }
            case "BTCUSDT" -> {
                btcRepo.save(representationToBtcData(representation));
                log.info("new {} trade registered", representation.symbol());
            }
            case "XRPUSDT" -> {
                xrpRepo.save(representationToXrpData(representation));
                log.info("new {} trade registered", representation.symbol());
            }
        }
    }

    protected static BtcUsdtData representationToBtcData(TradeDataRepresentation representation) {
        BtcUsdtData tradeData = new BtcUsdtData();
        tradeData.setType(representation.type());
        tradeData.setEventTime(representation.eventTime());
        tradeData.setSymbol(representation.symbol());
        tradeData.setTradeId(representation.tradeId());
        tradeData.setPrice(representation.price());
        tradeData.setQuantity(representation.quantity());
        tradeData.setTradeTime(representation.tradeTime());
        tradeData.setMarketMaker(representation.marketMaker());

        return tradeData;
    }

    protected static XrpUsdtData representationToXrpData(TradeDataRepresentation representation) {
        XrpUsdtData tradeData = new XrpUsdtData();
        tradeData.setType(representation.type());
        tradeData.setEventTime(representation.eventTime());
        tradeData.setSymbol(representation.symbol());
        tradeData.setTradeId(representation.tradeId());
        tradeData.setPrice(representation.price());
        tradeData.setQuantity(representation.quantity());
        tradeData.setTradeTime(representation.tradeTime());
        tradeData.setMarketMaker(representation.marketMaker());

        return tradeData;
    }

    protected static BnbUsdtData representationToBnbData(TradeDataRepresentation representation) {
        BnbUsdtData tradeData = new BnbUsdtData();
        tradeData.setType(representation.type());
        tradeData.setEventTime(representation.eventTime());
        tradeData.setSymbol(representation.symbol());
        tradeData.setTradeId(representation.tradeId());
        tradeData.setPrice(representation.price());
        tradeData.setQuantity(representation.quantity());
        tradeData.setTradeTime(representation.tradeTime());
        tradeData.setMarketMaker(representation.marketMaker());

        return tradeData;
    }

}
