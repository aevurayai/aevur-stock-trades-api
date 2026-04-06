package com.dvtsoftware.stocktrade.dto.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.math.BigDecimal;

@JsonPropertyOrder({"symbol", "highest", "lowest"})
public record PriceSummaryResponse(String symbol, BigDecimal highest, BigDecimal lowest) {
}
