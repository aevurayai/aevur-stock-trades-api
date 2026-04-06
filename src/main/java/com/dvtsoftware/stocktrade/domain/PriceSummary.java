package com.dvtsoftware.stocktrade.domain;

import java.math.BigDecimal;

public record PriceSummary(String symbol, BigDecimal highest, BigDecimal lowest) {
}
