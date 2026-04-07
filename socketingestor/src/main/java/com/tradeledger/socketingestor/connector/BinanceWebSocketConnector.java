package com.tradeledger.socketingestor.connector;

import com.tradeledger.socketingestor.representation.MessageTitleRepresentation;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.client.ReactorNettyWebSocketClient;
import org.springframework.web.reactive.socket.client.WebSocketClient;
import tools.jackson.databind.ObjectMapper;

import java.net.URI;


@RequiredArgsConstructor
@Slf4j
@Component
public class BinanceWebSocketConnector {

    private static final String URL = "wss://stream.binance.com:9443/ws/btcusdt@trade";
    private final ObjectMapper objectMapper;


    @PostConstruct
    public void ingest() {
        WebSocketClient ws = new ReactorNettyWebSocketClient();
        ws.execute(URI.create(URL), session -> session.receive()
                .map(WebSocketMessage::getPayloadAsText
                )
                .doOnNext(json -> {
                            MessageTitleRepresentation title = objectMapper.readValue(json, MessageTitleRepresentation.class);
                            log.info("{}{}", title.toKafkaKey(), json);
                        }
                ).then()).subscribe();
    }

}
