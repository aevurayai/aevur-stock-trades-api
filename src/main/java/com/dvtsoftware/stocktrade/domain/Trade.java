package com.dvtsoftware.stocktrade.domain;

import com.dvtsoftware.stocktrade.domain.entity.TradeUser;
import com.dvtsoftware.stocktrade.domain.enums.TradeType;

import java.math.BigDecimal;
import java.time.Instant;

public record Trade(
        Long id,
        TradeType type,
        TradeUser user,
        String symbol,
        Integer shares,
        BigDecimal price,
        Instant timestamp
) {
}