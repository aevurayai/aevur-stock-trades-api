package com.dvtsoftware.stocktrade.util;


import java.math.BigDecimal;

import org.springframework.stereotype.Component;

@Component
public class AppUtils {

    public BigDecimal normalize(BigDecimal value) {
        BigDecimal stripped = value.stripTrailingZeros();
        return stripped.scale() < 0 ? stripped.setScale(0) : stripped;
    }
}
