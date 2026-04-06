package com.dvtsoftware.stocktrade.util;

import com.dvtsoftware.stocktrade.domain.PriceSummary;
import com.dvtsoftware.stocktrade.dto.response.PriceSummaryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PriceSummaryUtils {

    private final AppUtils utils;

    public PriceSummaryResponse toResponse(PriceSummary summary){
        return new PriceSummaryResponse(summary.symbol(),
                utils.normalize(summary.highest()),
                utils.normalize(summary.lowest())
        );

    }

}
