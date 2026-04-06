package com.dvtsoftware.stocktrade.domain.enums;

import com.dvtsoftware.stocktrade.exception.BadRequestException;

import java.util.Arrays;

public enum TradeType {
    BUY("buy"),
    SELL("sell");

    private final String value;

    TradeType(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

    public static TradeType from(String rawValue) {
        return Arrays.stream(values())
                .filter(type -> type.value.equalsIgnoreCase(rawValue))
                .findFirst()
                .orElseThrow(() -> new BadRequestException("type must be buy or sell"));
    }
}

