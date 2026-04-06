package com.dvtsoftware.stocktrade.util;

import com.dvtsoftware.stocktrade.domain.Trade;
import com.dvtsoftware.stocktrade.dto.UserDto;
import com.dvtsoftware.stocktrade.dto.request.TradeRequest;
import com.dvtsoftware.stocktrade.dto.response.TradeResponse;
import com.dvtsoftware.stocktrade.domain.entity.TradeEntity;
import com.dvtsoftware.stocktrade.domain.entity.TradeUser;
import com.dvtsoftware.stocktrade.domain.enums.TradeType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TradeUtils {

    private final GmtDateTimeMapper gmtDateTimeMapper;

    public Trade toTrade(TradeRequest request) {
        return new Trade(
                request.getId(),
                TradeType.from(request.getType()),
                new TradeUser(request.getUser().getId(), request.getUser().getName()),
                request.getSymbol(),
                request.getShares(),
                request.getPrice(),
                gmtDateTimeMapper.parseTradeTimestamp(request.getTimestamp())
        );

    }

    public Trade toTrade(TradeEntity entity) {
        return new Trade(
                entity.getId(),
                entity.getType(),
                new TradeUser(entity.getUserId(), entity.getUserName()),
                entity.getSymbol(),
                entity.getShares(),
                entity.getPrice(),
                entity.getTimestamp()
        );
    }

    public TradeEntity toEntity(Trade trade) {

        TradeEntity entity = new TradeEntity();
        entity.setId(trade.id());
        entity.setType(trade.type());
        entity.setUserId(trade.user().getId());
        entity.setUserName(trade.user().getName());
        entity.setSymbol(trade.symbol());
        entity.setShares(trade.shares());
        entity.setPrice(trade.price());
        entity.setTimestamp(trade.timestamp());
        return entity;

    }

    public TradeResponse toResponse(Trade trade) {

        TradeResponse response = new TradeResponse();
        response.setId(trade.id());
        response.setType(trade.type().value());
        response.setUser(new UserDto(trade.user().getId(), trade.user().getName()));
        response.setSymbol(trade.symbol());
        response.setShares(trade.shares());
        response.setPrice(trade.price());
        response.setTimestamp(gmtDateTimeMapper.formatTradeTimestamp(trade.timestamp()));

        return response;

    }
}
