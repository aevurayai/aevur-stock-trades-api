package com.dvtsoftware.stocktrade.metrics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

@Component
public class TradeMetricsRecorder {

    private final Counter tradeCreatedCounter;
    private final Counter tradeCreateRejectedCounter;
    private final Counter tradeReadCounter;
    private final Counter tradesByUserQueryCounter;
    private final Counter tradesByStockFilterQueryCounter;
    private final Counter stockPriceQueryCounter;
    private final Counter tradeEraseCounter;

    public TradeMetricsRecorder(MeterRegistry meterRegistry) {
        this.tradeCreatedCounter = meterRegistry.counter("trade.api.trade.created");
        this.tradeCreateRejectedCounter = meterRegistry.counter("trade.api.trade.create.rejected");
        this.tradeReadCounter = meterRegistry.counter("trade.api.trade.read");
        this.tradesByUserQueryCounter = meterRegistry.counter("trade.api.trade.user.query");
        this.tradesByStockFilterQueryCounter = meterRegistry.counter("trade.api.trade.stock.filter.query");
        this.stockPriceQueryCounter = meterRegistry.counter("trade.api.stock.price.query");
        this.tradeEraseCounter = meterRegistry.counter("trade.api.trade.erase");
    }

    public void recordTradeCreated() {
        tradeCreatedCounter.increment();
    }

    public void recordTradeCreateRejected() {
        tradeCreateRejectedCounter.increment();
    }

    public void recordTradeRead() {
        tradeReadCounter.increment();
    }

    public void recordTradesByUserQuery() {
        tradesByUserQueryCounter.increment();
    }

    public void recordTradesByStockFilterQuery() {
        tradesByStockFilterQueryCounter.increment();
    }

    public void recordStockPriceQuery() {
        stockPriceQueryCounter.increment();
    }

    public void recordTradeErase() {
        tradeEraseCounter.increment();
    }
}