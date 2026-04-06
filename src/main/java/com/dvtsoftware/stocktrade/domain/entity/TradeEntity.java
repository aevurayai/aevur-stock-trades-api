package com.dvtsoftware.stocktrade.domain.entity;

import com.dvtsoftware.stocktrade.domain.enums.TradeType;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TradeEntity {

    private Long id;

    private TradeType type;

    private Long userId;

    private String userName;

    private String symbol;

    private Integer shares;

    private BigDecimal price;

    private Instant timestamp;
}
