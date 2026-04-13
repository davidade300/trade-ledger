CREATE TABLE btc_usdt
-- PREFIXOS MANTIDOS PARA IDENTIFICACAO DO CAMPO NO CONECTOR DO WEBSOCKET
(
    "e_trade"        VARCHAR(80),
    "E_event_time"   TIMESTAMP,
    "s_symbol"       VARCHAR(80),
    "t_trade_id"     INTEGER,
    "p_price"        NUMERIC,
    "q_quantity"     NUMERIC,
    "T_trade_time"   TIMESTAMP,
    "m_market_maker" BOOLEAN,
    "M_ignore"       BOOLEAN
);


CREATE TABLE bnb_usdt
(
    "e_trade"        VARCHAR(80),
    "E_event_time"   TIMESTAMP,
    "s_symbol"       VARCHAR(80),
    "t_trade_id"     INTEGER,
    "p_price"        NUMERIC,
    "q_quantity"     NUMERIC,
    "T_trade_time"   TIMESTAMP,
    "m_market_maker" BOOLEAN,
    "M_ignore"       BOOLEAN
);


CREATE TABLE xrp_usdt
(
    "e_trade"        VARCHAR(80),
    "E_event_time"   TIMESTAMP,
    "s_symbol"       VARCHAR(80),
    "t_trade_id"     INTEGER,
    "p_price"        NUMERIC,
    "q_quantity"     NUMERIC,
    "T_trade_time"   TIMESTAMP,
    "m_market_maker" BOOLEAN,
    "M_ignore"       BOOLEAN
);