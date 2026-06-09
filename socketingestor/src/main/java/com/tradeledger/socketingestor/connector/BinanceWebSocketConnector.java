package com.tradeledger.socketingestor.connector;

import com.tradeledger.socketingestor.publisher.MarketDataPublisher;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.client.ReactorNettyWebSocketClient;
import org.springframework.web.reactive.socket.client.WebSocketClient;
import reactor.util.retry.Retry;

import java.net.URI;
import java.time.Duration;


@RequiredArgsConstructor
@Slf4j
@Component
public class BinanceWebSocketConnector {

    private static final String URL = "wss://stream.binance.com:9443/ws/btcusdt@trade/bnbusdt@trade/xrpusdt@trade";
    private final MarketDataPublisher publisher;


    /**
     * Conecta ao Websocket da Binance e chama o publisher para publicar cada mensagem
     */
    @PostConstruct
    public void ingest() {
        WebSocketClient ws = new ReactorNettyWebSocketClient();
        ws.execute(URI.create(URL), session -> session.receive().map(WebSocketMessage::getPayloadAsText)
                        .doOnNext(publisher::publish).then()
                        )
                .retryWhen(Retry.fixedDelay(Long.MAX_VALUE, Duration.ofSeconds(5))
                        .maxBackoff(Duration.ofMinutes(2))
                        .doBeforeRetry(msg -> log.warn("Connection dropped, reconnect attempt N {}", msg.totalRetries())))
                .repeat()
                .subscribe(null, e -> log.error("Fatal error occured in the connector: {} , {}", e.getClass(), e.getMessage()));
    }


    @PreDestroy
    public void onShutdown() {
        log.info("Shutting down");
    }
}
