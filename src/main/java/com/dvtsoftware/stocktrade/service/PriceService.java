package com.dvtsoftware.stocktrade.service;

import com.dvtsoftware.stocktrade.dao.TradeDAO;
import com.dvtsoftware.stocktrade.domain.PriceSummary;
import com.dvtsoftware.stocktrade.dto.response.PriceSummaryResponse;
import com.dvtsoftware.stocktrade.domain.entity.PriceExtremes;
import com.dvtsoftware.stocktrade.exception.BadRequestException;
import com.dvtsoftware.stocktrade.exception.NotFoundException;
import com.dvtsoftware.stocktrade.metrics.TradeMetricsRecorder;
import com.dvtsoftware.stocktrade.util.AppUtils;
import com.dvtsoftware.stocktrade.util.GmtDateTimeMapper;
import com.dvtsoftware.stocktrade.util.PriceSummaryUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Optional;

@Service
public class PriceService {
    private final TradeDAO tradeDAO;
    private final GmtDateTimeMapper gmtDateTimeMapper;
    private final PriceSummaryUtils utils;
    private final AppUtils appUtils;
    private final TradeMetricsRecorder tradeMetricsRecorder;

    public PriceService(TradeDAO tradeDAO,
                        GmtDateTimeMapper gmtDateTimeMapper,
                        PriceSummaryUtils utils,
                        AppUtils appUtils,
                        TradeMetricsRecorder tradeMetricsRecorder){
        this.tradeDAO = tradeDAO;
        this.gmtDateTimeMapper = gmtDateTimeMapper;
        this.utils = utils;
        this.appUtils = appUtils;
        this.tradeMetricsRecorder = tradeMetricsRecorder;
    }

    @Cacheable(cacheNames = "stockPriceRange", key = "#symbol + '|' + #start + '|' + #end")
    @Transactional(readOnly = true)
    public Optional<PriceSummaryResponse> getHighAndLowForSymbol(String symbol, String start, String end){

        LocalDate startDate = gmtDateTimeMapper.parseDate(start, "start");
        LocalDate endDate = gmtDateTimeMapper.parseDate(end, "end");

        if(startDate.isAfter(endDate)){
            throw new BadRequestException("Start date must be on or before the end date");
        }

        if(!tradeDAO.symbolExists(symbol)){
            throw new NotFoundException("Stock symbol not found");
        }

        tradeMetricsRecorder.recordStockPriceQuery();

        Instant startInclusive = startDate.atStartOfDay().toInstant(ZoneOffset.UTC);
        Instant endExclusive = endDate.plusDays(1).atStartOfDay().toInstant(ZoneOffset.UTC);

        PriceExtremes priceExtremes = tradeDAO.findSymbolPriceExtremes(symbol, startInclusive, endExclusive);

        if(priceExtremes == null || priceExtremes.highest() == null || priceExtremes.lowest() == null){
            return Optional.empty();
        }

        return Optional.of(utils.toResponse(new PriceSummary(symbol,
                appUtils.normalize(priceExtremes.highest()),
                appUtils.normalize(priceExtremes.lowest())
        )));

    }



}
