package com.tradeledger.tradeprocessor.Subscriber.representation;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.Instant;

@JsonIgnoreProperties(ignoreUnknown = true)
public record TradeDataRepresentation(
        @JsonProperty("e") String type,
        @JsonProperty("E") Instant eventTime,
        @JsonProperty("s") String symbol,
        @JsonProperty("t") Long tradeId,
        @JsonProperty("p") BigDecimal price,
        @JsonProperty("q") BigDecimal quantity,
        @JsonProperty("T") Instant tradeTime,
        @JsonProperty("m") Boolean marketMaker) {
}
    