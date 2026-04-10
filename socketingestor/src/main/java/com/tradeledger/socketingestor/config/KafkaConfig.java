package com.tradeledger.socketingestor.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfig {

    @Value("${tradeledger.config.kafka.server-url}")
    private String kafkaServerUrl;

    //TODO: voltar aqui depois de finalizar tratamento de excecao, logging e mecanismos de reconnect no conector de socket
//    @Bean
//    public ProducerFactory<String, String> producerFactory() {
//        Map<String, Object> props = new HashMap<>();
//    }
}
