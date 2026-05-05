package com.tradeledger.tradeprocessor.Subscriber;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tradeledger.tradeprocessor.Subscriber.representation.TradeDataRepresentation;
import com.tradeledger.tradeprocessor.services.TradeRoutingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TradeDataSubscriber {

    private final TradeRoutingService service;
    private final ObjectMapper objectMapper;

    public TradeDataSubscriber(TradeRoutingService service, ObjectMapper objectMapper) {
        this.service = service;
        this.objectMapper = objectMapper;

    }


    @KafkaListener(groupId = "${spring.kafka.consumer.group-id}", topics = {"${tradeledger.config.kafka.topics.market-data}"})
    public void receiveTradeData(String json) {
        log.info("Iniciando subscricao");
        try {
            var data = objectMapper.readValue(json, TradeDataRepresentation.class);
            log.info("\ntrade id:{}\nquantity: {}\ntrade pair: {}\nprice: {}", data.tradeId(), data.quantity(), data.symbol(), data.price());
            service.route(data);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}

