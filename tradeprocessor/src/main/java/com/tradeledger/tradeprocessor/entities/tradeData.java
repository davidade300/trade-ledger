package com.tradeledger.tradeprocessor.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;

@MappedSuperclass
public abstract class tradeData {
    // TODO: se funcionar, incluir definicao (nome, etc) das colunas
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String type;
    @Column
    private Timestamp eventTime;
    @Column
    private String symbol;
    @Column
    private BigInteger tradeId;
    @Column
    private BigDecimal price;
    @Column
    private BigDecimal quantity;
    @Column
    private Timestamp tradeTime;
    @Column
    private Boolean marketMaker;
}
