package com.tradeledger.socketingestor.publisher;

import com.tradeledger.socketingestor.representation.MessageTitleRepresentation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

@Component
@Slf4j
public class MarketDataPublisher {


    @Value("${tradeledger.config.kafka.topics.market-data}")
    private String topic;

    private final ObjectMapper objectMapper;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public MarketDataPublisher(ObjectMapper objectMapper, KafkaTemplate<String, String> kafkaTemplate) {
        this.objectMapper = objectMapper;
        this.kafkaTemplate = kafkaTemplate;
    }

    /**
     * @param data string contendo os dados de uma mensagem a ser publicada no topico de trades.
     */
    public void publish(String data) {
        try {
            MessageTitleRepresentation repr = objectMapper.readValue(data, MessageTitleRepresentation.class);
            kafkaTemplate.send(topic, repr.symbol(), data);
        } catch (Exception e) {
            log.error("An exception of type {} occured: {}", e.getClass(), e.getMessage());
        }
    }
}
