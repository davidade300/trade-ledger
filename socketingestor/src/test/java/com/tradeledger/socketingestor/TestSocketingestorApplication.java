package com.tradeledger.socketingestor;

import org.springframework.boot.SpringApplication;

public class TestSocketingestorApplication {

	public static void main(String[] args) {
		SpringApplication.from(SocketingestorApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
