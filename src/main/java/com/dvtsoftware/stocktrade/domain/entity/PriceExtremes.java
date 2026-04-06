package com.dvtsoftware.stocktrade.domain.entity;

import java.math.BigDecimal;

public record PriceExtremes(BigDecimal highest, BigDecimal lowest)  {
}
