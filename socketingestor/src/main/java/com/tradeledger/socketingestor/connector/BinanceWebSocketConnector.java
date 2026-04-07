package com.tradeledger.socketingestor.connector;

import com.tradeledger.socketingestor.representation.MessageTitleRepresentation;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.client.ReactorNettyWebSocketClient;
import org.springframework.web.reactive.socket.client.WebSocketClient;

import java.net.URI;


@RequiredArgsConstructor
@Slf4j
@Component
public class BinanceWebSocketConnector {

    private static final String URL = "wss://stream.binance.com:9443/ws/btcusdt@trade";

    @PostConstruct
    public void listen() {
        WebSocketClient ws = new ReactorNettyWebSocketClient();
        ws.execute(URI.create(URL), session -> session.receive()
                .map(WebSocketMessage::getPayloadAsText)
                .doOnNext(System.out::println).then()).subscribe();
    }

    private MessageTitleRepresentation MessageTitle(String message) {
        return new MessageTitleRepresentation(message);
    }

}
