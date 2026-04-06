package com.dvtsoftware.stocktrade.service;


import com.dvtsoftware.stocktrade.dao.TradeDAO;
import com.dvtsoftware.stocktrade.domain.Trade;
import com.dvtsoftware.stocktrade.dto.request.TradeRequest;
import com.dvtsoftware.stocktrade.dto.response.TradeResponse;
import com.dvtsoftware.stocktrade.domain.entity.TradeEntity;
import com.dvtsoftware.stocktrade.domain.enums.TradeType;
import com.dvtsoftware.stocktrade.exception.BadRequestException;
import com.dvtsoftware.stocktrade.exception.NotFoundException;
import com.dvtsoftware.stocktrade.metrics.TradeMetricsRecorder;
import com.dvtsoftware.stocktrade.util.GmtDateTimeMapper;
import com.dvtsoftware.stocktrade.util.TradeUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TradeService {

    private final TradeUtils utils;
    private final GmtDateTimeMapper gmtDateTimeMapper;

    private final TradeDAO tradeDAO;
    private final TradeMetricsRecorder tradeMetricsRecorder;

    @Caching(evict = {
            @CacheEvict(cacheNames = "tradeById", allEntries = true),
            @CacheEvict(cacheNames = "allTrades", allEntries = true),
            @CacheEvict(cacheNames = "tradesByUser", allEntries = true),
            @CacheEvict(cacheNames = "tradesByStockFilter", allEntries = true),
            @CacheEvict(cacheNames = "stockPriceRange", allEntries = true),
            @CacheEvict(cacheNames = "tradeSearch", allEntries = true)
    })
    @Transactional
    public void eraseAll() {
        log.info("Erasing all trades");
        tradeDAO.deleteAll();
        tradeMetricsRecorder.recordTradeErase();
    }

    @Caching(evict = {
            @CacheEvict(cacheNames = "tradeById", allEntries = true),
            @CacheEvict(cacheNames = "allTrades", allEntries = true),
            @CacheEvict(cacheNames = "tradesByUser", allEntries = true),
            @CacheEvict(cacheNames = "tradesByStockFilter", allEntries = true),
            @CacheEvict(cacheNames = "stockPriceRange", allEntries = true),
            @CacheEvict(cacheNames = "tradeSearch", allEntries = true)
    })
    @Transactional
    public TradeResponse create(TradeRequest request) {

        try {
            Trade trade = utils.toTrade(request);
            TradeEntity entity = utils.toEntity(trade);
            trade = tradeDAO.save(entity);
            tradeMetricsRecorder.recordTradeCreated();
            log.info("Trade created: id={}", entity.getId());

            return utils.toResponse(trade);

        } catch (DataIntegrityViolationException exception) {
            tradeMetricsRecorder.recordTradeCreateRejected();
            throw new BadRequestException("Trade with the same id already exists");
        }
    }

    @Cacheable(cacheNames = "tradeById", key = "#id")
    @Transactional(readOnly = true)
    public TradeResponse getById(Long id) {

        log.info("Getting Trade with ID {}", id);

        if (id == null || id < 1) {
            throw new BadRequestException("id must be positive");
        }
        Trade trade = utils.toTrade(tradeDAO.findById(id)
                .orElseThrow(() -> new NotFoundException("Trade not found")));
        tradeMetricsRecorder.recordTradeRead();
        return utils.toResponse(trade);
    }

    @Cacheable(cacheNames = "allTrades", key = "'all'")
    @Transactional(readOnly = true)
    public List<TradeResponse> getAll() {
        tradeMetricsRecorder.recordTradeRead();
        return tradeDAO.findAllOrderByIdAsc()
                .stream()
                .map(utils::toTrade)
                .map(utils::toResponse)
                .toList();
    }

    @Cacheable(cacheNames = "tradesByUser", key = "#userId")
    @Transactional(readOnly = true)
    public List<TradeResponse> getByUserId(Long userId) {
        if (userId == null || userId < 1) {
            throw new BadRequestException("userID must be positive");
        }

        List<TradeResponse> responses = tradeDAO.findTradesByUserId(userId)
                .stream()
                .map(utils::toTrade)
                .map(utils::toResponse)
                .toList();

        if (responses.isEmpty()) {
            throw new NotFoundException("No trades found for the requested user");
        }

        tradeMetricsRecorder.recordTradesByUserQuery();
        return responses;
    }

    @Cacheable(cacheNames = "tradesByStockFilter", key = "#symbol + '|' + #type + '|' + #start + '|' + #end")
    @Transactional(readOnly = true)
    public List<TradeResponse> findBySymbolTypeAndDateRange(String symbol, String type, String start, String end) {

        LocalDate startDate = gmtDateTimeMapper.parseDate(start, "start");
        LocalDate endDate = gmtDateTimeMapper.parseDate(end, "end");

        if (startDate.isAfter(endDate)) {
            throw new BadRequestException("Start date must be on or before the end date");
        }

        if (!tradeDAO.symbolExists(symbol)) {
            throw new NotFoundException("Stock symbol not found");
        }

        Instant startInclusive = startDate.atStartOfDay().toInstant(ZoneOffset.UTC);
        Instant endExclusive = endDate.plusDays(1).atStartOfDay().toInstant(ZoneOffset.UTC);

        List<TradeResponse> response = tradeDAO.findAllBySymbolAndTypeAndDate(
                        symbol,
                        TradeType.from(type),
                        startInclusive,
                        endExclusive)
                .stream()
                .map(utils::toTrade)
                .map(utils::toResponse)
                .toList();

        tradeMetricsRecorder.recordTradesByStockFilterQuery();
        return response;

    }

}
