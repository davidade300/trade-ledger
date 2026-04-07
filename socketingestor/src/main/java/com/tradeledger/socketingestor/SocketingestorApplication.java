package com.tradeledger.socketingestor;

import com.tradeledger.socketingestor.connector.BinanceWebSocketConnector;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SocketingestorApplication {

    public static void main(String[] args) {
        SpringApplication.run(SocketingestorApplication.class, args);
    }

}
