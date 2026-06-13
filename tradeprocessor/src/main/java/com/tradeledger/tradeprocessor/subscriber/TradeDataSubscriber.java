package com.tradeledger.tradeprocessor.subscriber;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tradeledger.tradeprocessor.representation.TradeDataRepresentation;
import com.tradeledger.tradeprocessor.services.TradeIngestionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TradeDataSubscriber {

    private final TradeIngestionService service;
    private final ObjectMapper objectMapper;

    public TradeDataSubscriber(TradeIngestionService service, ObjectMapper objectMapper) {
        this.service = service;
        this.objectMapper = objectMapper;

    }


    @KafkaListener(groupId = "${spring.kafka.consumer.group-id}", topics = {"${tradeledger.config.kafka.topics.market-data}"})
    public void persistTradeData(String json) {
        try {
            var data = objectMapper.readValue(json, TradeDataRepresentation.class);
            service.persist(data);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

}

