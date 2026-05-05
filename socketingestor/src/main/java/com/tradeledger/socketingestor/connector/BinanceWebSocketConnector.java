package com.tradeledger.socketingestor.connector;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.client.ReactorNettyWebSocketClient;
import org.springframework.web.reactive.socket.client.WebSocketClient;
import reactor.util.retry.Retry;
import tools.jackson.databind.ObjectMapper;

import java.net.URI;
import java.time.Duration;


@RequiredArgsConstructor
@Slf4j
@Component
public class BinanceWebSocketConnector {

    private static final String URL = "wss://stream.binance.com:9443/ws/btcusdt@trade/bnbusdt@trade/xrpusdt@trade";
    private final ObjectMapper objectMapper;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Value("${tradeledger.config.kafka.topics.market-data}")
    private String topic;

    /**
     * Conecta ao Websocket da Binance e publica as mensagens em uma particação do topico kafka
     * de mensagens de trades, cada par possui uma particao propria
     * TODO 1: publicar as mensagens no kafka
     * TODO 2: Adicionar restart no container para caso a aplicacao caia
     */
    @PostConstruct
    public void ingest() {
        WebSocketClient ws = new ReactorNettyWebSocketClient();
        ws.execute(URI.create(URL), session -> session.receive().map(WebSocketMessage::getPayloadAsText)
                        .doOnNext(data -> {
                            try {

//                                MessageTitleRepresentation title = objectMapper.readValue(data, MessageTitleRepresentation.class);
                                kafkaTemplate.send(topic, data);
//                                log.info("{}{}", title.symbol(), data);
                                log.info(data);

                            } catch (Exception e) {
                                log.error("An exception of type {} occured: {}", e.getClass(), e.getMessage());
                            }
                        })
                        .then())
                .retryWhen(Retry.fixedDelay(Long.MAX_VALUE, Duration.ofSeconds(5))
                        .maxBackoff(Duration.ofMinutes(2))
                        .doBeforeRetry(msg -> log.warn("Connection dropped, reconnect attempt N {}", msg.totalRetries())))
                .repeat()
                .subscribe(null, e -> log.error("Fatal error occured in the connector: {} , {}", e.getClass(), e.getMessage()));
    }


    /**
     * TODO: ver se isso realmente e necessario.
     * o .ingest() ja trata excecoes e reinicia o socket quando ele for encerrado
     * e a aplicacao sera reiniciada quando o container tiver restart
     */
    @PreDestroy
    public void onShutdown() {
        log.info("Shutting down");
    }
}
