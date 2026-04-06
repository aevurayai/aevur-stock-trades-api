package com.dvtsoftware.stocktrade.util;

import com.dvtsoftware.stocktrade.exception.BadRequestException;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.Locale;

@Component
public class GmtDateTimeMapper {

    private static final DateTimeFormatter TIMESTAMP_FORMATTER = DateTimeFormatter
            .ofPattern("uuuu-MM-dd HH:mm:ss", Locale.ROOT)
            .withResolverStyle(ResolverStyle.STRICT);

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter
            .ofPattern("uuuu-MM-dd", Locale.ROOT)
            .withResolverStyle(ResolverStyle.STRICT);

    public Instant parseTradeTimestamp(String timestamp) {
        try {
            LocalDateTime localDateTime = LocalDateTime.parse(timestamp, TIMESTAMP_FORMATTER);
            return localDateTime.toInstant(ZoneOffset.UTC);
        } catch (DateTimeParseException exception) {
            throw new BadRequestException("timestamp must use format yyyy-MM-dd HH:mm:ss in GMT");
        }
    }

    public String formatTradeTimestamp(Instant timestamp) {
        return TIMESTAMP_FORMATTER.format(timestamp.atOffset(ZoneOffset.UTC));
    }

    public LocalDate parseDate(String value, String fieldName) {
        try {
            return LocalDate.parse(value, DATE_FORMATTER);
        } catch (DateTimeParseException exception) {
            throw new BadRequestException(fieldName + " must use format yyyy-MM-dd");
        }
    }
}