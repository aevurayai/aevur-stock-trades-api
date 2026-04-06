package com.dvtsoftware.stocktrade.dto.response;

import com.dvtsoftware.stocktrade.dto.UserDto;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import java.math.BigDecimal;

@JsonPropertyOrder({"id", "type", "user", "symbol", "shares", "price", "timestamp"})
@Data
public class TradeResponse {
    private Long id;
    private String type;
    private UserDto user;
    private String symbol;
    private Integer shares;
    private BigDecimal price;
    private String timestamp;
}

