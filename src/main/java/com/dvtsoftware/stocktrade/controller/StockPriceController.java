package com.dvtsoftware.stocktrade.controller;

import com.dvtsoftware.stocktrade.dto.response.MessageResponse;
import com.dvtsoftware.stocktrade.service.PriceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("${assessment.api.base-path:}")
public class StockPriceController {
    private final PriceService priceService;

    @GetMapping("/stocks/{stockSymbol}/price")
    public ResponseEntity<?> getSymbolPriceRange(
            @PathVariable("stockSymbol") String stockSymbol,
            @RequestParam("start") String start,
            @RequestParam("end") String end
    ){

        return priceService.getHighAndLowForSymbol(stockSymbol, start, end)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.ok(new MessageResponse("There are no trades in the given date range")));

    }
}
