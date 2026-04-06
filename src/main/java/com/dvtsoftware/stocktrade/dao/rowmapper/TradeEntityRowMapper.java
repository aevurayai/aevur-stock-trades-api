//package com.dvtsoftware.stocktrade.dao.rowmapper;
//
//
//import com.dvtsoftware.stocktrade.domain.entity.TradeEntity;
//import com.dvtsoftware.stocktrade.domain.enums.TradeType;
//import org.springframework.jdbc.core.RowMapper;
//
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.time.OffsetDateTime;
//
//public class TradeEntityRowMapper implements RowMapper<TradeEntity> {
//
//    @Override
//    public TradeEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
//        TradeEntity tradeEntity = new TradeEntity();
//
//        tradeEntity.setId(rs.getLong("T_UID"));
//        tradeEntity.setType(TradeType.from(rs.getString("T_TYPE")));
//        tradeEntity.setUserId(rs.getLong("T_U_UID"));
//        tradeEntity.setUserName(rs.getString("T_U_NAME"));
//        tradeEntity.setSymbol(rs.getString("T_SYMBOL"));
//        tradeEntity.setShares(rs.getInt("T_SHARES"));
//        tradeEntity.setPrice(rs.getBigDecimal("T_PRICE"));
//        tradeEntity.setTimestamp(rs.getObject("T_CREATED_AT", OffsetDateTime.class).toInstant());
//
//
//        return tradeEntity;
//    }
//}
