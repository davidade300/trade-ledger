package com.tradeledger.socketingestor.representation;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record MessageTitleRepresentation(@JsonProperty("s") String symbol) {


}
