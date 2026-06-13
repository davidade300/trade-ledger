package com.tradeledger.tradeprocessor.services;

import com.tradeledger.tradeprocessor.entities.MarketTrade;
import com.tradeledger.tradeprocessor.repositories.MarketTradeRepository;
import com.tradeledger.tradeprocessor.representation.TradeDataRepresentation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class TradeIngestionService {
    private final MarketTradeRepository marketTradeRepository;

    public TradeIngestionService(MarketTradeRepository marketTradeRepository) {
        this.marketTradeRepository = marketTradeRepository;
    }

    public void persist(TradeDataRepresentation representation) {
        marketTradeRepository.save(representationToMarketTrade(representation));
    }

    protected static MarketTrade representationToMarketTrade(TradeDataRepresentation representation) {
        MarketTrade trade = new MarketTrade();
        trade.setType(representation.type());
        trade.setEventTime(representation.eventTime());
        trade.setSymbol(representation.symbol());
        trade.setTradeId(representation.tradeId());
        trade.setPrice(representation.price());
        trade.setQuantity(representation.quantity());
        trade.setTradeTime(representation.tradeTime());
        trade.setMarketMaker(representation.marketMaker());

        return trade;
    }
}
