package com.tradeledger.tradeprocessor;

import org.springframework.boot.SpringApplication;

public class TestTradeprocessorApplication {

	public static void main(String[] args) {
		SpringApplication.from(TradeprocessorApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
