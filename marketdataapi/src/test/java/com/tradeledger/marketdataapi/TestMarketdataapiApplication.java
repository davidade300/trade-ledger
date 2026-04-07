package com.tradeledger.marketdataapi;

import org.springframework.boot.SpringApplication;

public class TestMarketdataapiApplication {

	public static void main(String[] args) {
		SpringApplication.from(MarketdataapiApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
