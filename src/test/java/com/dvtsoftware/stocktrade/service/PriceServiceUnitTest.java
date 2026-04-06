package com.dvtsoftware.stocktrade.service;

import com.dvtsoftware.stocktrade.dao.TradeDAO;
import com.dvtsoftware.stocktrade.domain.entity.PriceExtremes;
import com.dvtsoftware.stocktrade.dto.response.PriceSummaryResponse;
import com.dvtsoftware.stocktrade.exception.BadRequestException;
import com.dvtsoftware.stocktrade.exception.NotFoundException;
import com.dvtsoftware.stocktrade.metrics.TradeMetricsRecorder;
import com.dvtsoftware.stocktrade.util.AppUtils;
import com.dvtsoftware.stocktrade.util.GmtDateTimeMapper;
import com.dvtsoftware.stocktrade.util.PriceSummaryUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PriceServiceUnitTest {

    @Mock
    private TradeDAO tradeDAO;

    @Mock
    private TradeMetricsRecorder tradeMetricsRecorder;

    private PriceService priceService;

    @BeforeEach
    void setUp() {
        AppUtils appUtils = new AppUtils();
        priceService = new PriceService(
                tradeDAO,
                new GmtDateTimeMapper(),
                new PriceSummaryUtils(appUtils),
                appUtils,
                tradeMetricsRecorder
        );
    }

    @Test
    void returnsHighestAndLowestPriceWhenTradesExistInRange() {
        when(tradeDAO.symbolExists("A")).thenReturn(true);
        when(tradeDAO.findSymbolPriceExtremes(eq("A"), eq(Instant.parse("2016-12-29T00:00:00Z")), eq(Instant.parse("2017-01-04T00:00:00Z"))))
                .thenReturn(new PriceExtremes(new BigDecimal("149.35"), new BigDecimal("135.89")));

        Optional<PriceSummaryResponse> result = priceService.getHighAndLowForSymbol("A", "2016-12-29", "2017-01-03");

        assertTrue(result.isPresent());
        assertEquals(new PriceSummaryResponse("A", new BigDecimal("149.35"), new BigDecimal("135.89")), result.get());
        verify(tradeMetricsRecorder).recordStockPriceQuery();
    }

    @Test
    void returnsEmptyWhenSymbolExistsButNoTradesMatchTheDateRange() {
        when(tradeDAO.symbolExists("ZAYO")).thenReturn(true);
        when(tradeDAO.findSymbolPriceExtremes(eq("ZAYO"), eq(Instant.parse("2017-01-05T00:00:00Z")), eq(Instant.parse("2017-01-07T00:00:00Z"))))
                .thenReturn(new PriceExtremes(null, null));

        Optional<PriceSummaryResponse> result = priceService.getHighAndLowForSymbol("ZAYO", "2017-01-05", "2017-01-06");

        assertTrue(result.isEmpty());
        verify(tradeMetricsRecorder).recordStockPriceQuery();
    }

    @Test
    void usesAnInclusiveEndDateByQueryingUntilTheNextMidnightUtc() {
        when(tradeDAO.symbolExists("MMS")).thenReturn(true);
        when(tradeDAO.findSymbolPriceExtremes(eq("MMS"), org.mockito.ArgumentMatchers.any(), org.mockito.ArgumentMatchers.any()))
                .thenReturn(new PriceExtremes(new BigDecimal("183.45"), new BigDecimal("152.93")));

        priceService.getHighAndLowForSymbol("MMS", "2017-01-03", "2017-01-03");

        ArgumentCaptor<Instant> startCaptor = ArgumentCaptor.forClass(Instant.class);
        ArgumentCaptor<Instant> endCaptor = ArgumentCaptor.forClass(Instant.class);
        verify(tradeDAO).findSymbolPriceExtremes(eq("MMS"), startCaptor.capture(), endCaptor.capture());
        assertEquals(Instant.parse("2017-01-03T00:00:00Z"), startCaptor.getValue());
        assertEquals(Instant.parse("2017-01-04T00:00:00Z"), endCaptor.getValue());
    }

    @Test
    void throwsNotFoundWhenTheSymbolHasNeverBeenTraded() {
        when(tradeDAO.symbolExists("AB")).thenReturn(false);

        assertThrows(NotFoundException.class, () -> priceService.getHighAndLowForSymbol("AB", "2016-12-28", "2016-12-29"));
    }

    @Test
    void throwsBadRequestWhenStartDateIsAfterEndDate() {
        assertThrows(BadRequestException.class, () -> priceService.getHighAndLowForSymbol("A", "2017-01-04", "2017-01-03"));
    }
}
