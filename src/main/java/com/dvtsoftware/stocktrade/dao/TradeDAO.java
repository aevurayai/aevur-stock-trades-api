package com.dvtsoftware.stocktrade.dao;

import com.dvtsoftware.stocktrade.domain.Trade;
import com.dvtsoftware.stocktrade.domain.entity.PriceExtremes;
import com.dvtsoftware.stocktrade.domain.entity.TradeEntity;
import com.dvtsoftware.stocktrade.domain.enums.TradeType;
import com.dvtsoftware.stocktrade.util.SQLConstants;
import com.dvtsoftware.stocktrade.util.TradeUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Repository

public class TradeDAO {
    private final JdbcTemplate jdbcTemplate;

    private final TradeUtils utils;

    public TradeDAO(JdbcTemplate jdbcTemplate, TradeUtils utils) {
        this.jdbcTemplate = jdbcTemplate;
        this.utils = utils;
    }

    public void deleteAllInBatch() {
        jdbcTemplate.update(SQLConstants.DELETE_ALL_TRADES);
    }

    public void deleteAll() {
        deleteAllInBatch();
    }

    public Trade save(TradeEntity tradeEntity) {
        jdbcTemplate.update(SQLConstants.INSERT_INTO_TRADE,
                tradeEntity.getId(),
                tradeEntity.getType().value(),
                tradeEntity.getUserId(),
                tradeEntity.getUserName(),
                tradeEntity.getSymbol(),
                tradeEntity.getShares(),
                tradeEntity.getPrice(),
                tradeEntity.getTimestamp() != null ? tradeEntity.getTimestamp() : Instant.now());
        return utils.toTrade(tradeEntity);
    }

    public Optional<TradeEntity> findById(Long id) {
        List<TradeEntity> results = jdbcTemplate.query(SQLConstants.QUERY_TRADE_BY_ID,
                tradeRowMapper,
                id);
        return results.stream().findFirst();
    }

    public List<TradeEntity> findAllOrderByIdAsc() {
        return jdbcTemplate.query(SQLConstants.QUERY_ALL_TRADE_ORDER_BY_ID_ASC,
                tradeRowMapper
        );
    }

    public List<TradeEntity> findTradesByUserId(Long userId) {
        return jdbcTemplate.query(SQLConstants.QUERY_TRADES_BY_USER_ID,
                tradeRowMapper,
                userId);
    }

    public List<TradeEntity> findAllBySymbolAndTypeAndDate(String symbol, TradeType type, Instant startDate, Instant endDate) {
        return jdbcTemplate.query(SQLConstants.QUERY_TRADES_BY_SYMBOL_AND_DATE,
                tradeRowMapper,
                symbol,
                type.value(),
                startDate,
                endDate);
    }

    public boolean symbolExists(String symbol) {
        Boolean exists = jdbcTemplate.queryForObject(SQLConstants.QUERY_SYMBOL,
                Boolean.class,
                symbol);
        return Boolean.TRUE.equals(exists);
    }

    public PriceExtremes findSymbolPriceExtremes(String symbol, Instant startInclusive, Instant endExclusive) {
        List<PriceExtremes> results = jdbcTemplate.query(SQLConstants.QUERY_SYMBOL_PRICE_EXTREMES_SYMBOL,
                (rs, rowNum) -> new PriceExtremes(
                        rs.getBigDecimal("highest"),
                        rs.getBigDecimal("lowest")
                ),
                symbol,
                startInclusive,
                endExclusive);
        return results.isEmpty() ? null : results.get(0);
    }

    private final RowMapper<TradeEntity> tradeRowMapper = (resultSet, rowNum) -> {
        TradeEntity entity = new TradeEntity();
        entity.setId(resultSet.getLong("T_UID"));
        entity.setType(TradeType.from(resultSet.getString("T_TYPE")));
        entity.setUserId(resultSet.getLong("T_U_UID"));
        entity.setUserName(resultSet.getString("T_U_NAME"));
        entity.setSymbol(resultSet.getString("T_SYMBOL"));
        entity.setShares(resultSet.getInt("T_SHARES"));
        entity.setPrice(resultSet.getBigDecimal("T_PRICE"));
        entity.setTimestamp(resultSet.getObject("T_CREATED_AT", OffsetDateTime.class).toInstant());
        return entity;
    };

}
