package com.tradeledger.socketingestor.representation;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record MessageTitleRepresentation(@JsonProperty("s") String symbol) {

    public String toKafkaKey() {
        return symbol + " : ";
    }
}
