package com.dvtsoftware.stocktrade.util;

public class SQLConstants {

    private SQLConstants(){}

    private static final String TRADE_COLUMNS = "T_UID, " +
            "T_TYPE, " +
            "T_U_UID, " +
            "T_U_NAME, " +
            "T_SYMBOL, " +
            "T_SHARES, " +
            "T_PRICE, " +
            "T_CREATED_AT ";

    public static final String INSERT_INTO_TRADE_TO_TRADE_ARHCIVE = "" +
            "Insert Into TA_TRADE_ARHCIVE"+
            "Select * From T_TRADE";

    public static final String DELETE_ALL_TRADES="" +
            "Delete From T_TRADE";

    public static final String QUERY_TRADE_ARCHIVE_WITH_TRADE_ID="" +
            "Select * " +
            "From TA_TRADE_ARCHIVE " +
            "Where TA_T_UID = ? ";

    public static final String INSERT_INTO_TRADE="" +
            "Insert into T_TRADE(" +
                                  TRADE_COLUMNS +
                                ")" +
            " Values(?,?,?,?,?,?,?,?)";

    public static final String QUERY_TRADE_BY_ID ="" +
            "SELECT " + TRADE_COLUMNS +
            "FROM T_TRADE " +
            "WHERE T_UID = ?";

    public static final String QUERY_ALL_TRADE_ORDER_BY_ID_ASC ="" +
            "SELECT " + TRADE_COLUMNS +
            "FROM T_TRADE " +
            "ORDER BY T_UID ASC";

    public static final String QUERY_TRADES_BY_USER_ID ="" +
            "SELECT " + TRADE_COLUMNS +
            "FROM T_TRADE " +
            "WHERE T_U_UID = ? " +
            "ORDER BY T_UID ASC";

    public static final String QUERY_TRADES_BY_SYMBOL_AND_DATE ="" +
            "SELECT " + TRADE_COLUMNS +
            "FROM T_TRADE " +
            "WHERE T_SYMBOL = ? " +
            "AND T_TYPE = ? " +
            "AND T_CREATED_AT >= ? " +
            "AND T_CREATED_AT < ? " +
            "ORDER BY T_UID ASC";

    public static final String QUERY_SYMBOL = "" +
            "SELECT CASE " +
            "WHEN COUNT(1) > 0 THEN TRUE " +
            "ELSE FALSE " +
            "END " +
            "FROM T_TRADE " +
            "WHERE T_SYMBOL = ?";

    public static final String QUERY_SYMBOL_PRICE_EXTREMES_SYMBOL = "" +
            "SELECT MAX(T_PRICE) AS highest, MIN(T_PRICE) AS lowest " +
            "FROM T_TRADE " +
            "WHERE T_SYMBOL = ? " +
            "AND T_CREATED_AT >= ? " +
            "AND T_CREATED_AT < ?";

}
