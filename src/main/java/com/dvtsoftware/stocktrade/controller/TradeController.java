package com.dvtsoftware.stocktrade.controller;

import com.dvtsoftware.stocktrade.dto.request.TradeRequest;
import com.dvtsoftware.stocktrade.dto.response.TradeResponse;

import com.dvtsoftware.stocktrade.service.TradeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${assessment.api.base-path:}")
@Slf4j
@Tag(name="Trades", description = "Stock trade management endpoints")
public class TradeController {

    private final TradeService tradeService;

    @DeleteMapping("/erase")
    @Operation(summary = "Erase all trades", description = "Deletes all trade records from the system")
    @ApiResponse(responseCode = "200", description = "All trades erased")
    public ResponseEntity<Void> eraseAll(){
        tradeService.eraseAll();
        return ResponseEntity.ok().build();
    }

    @PostMapping("/trades")
    @Operation(summary = "Add a new trade")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Trade created"),
            @ApiResponse(responseCode = "400", description = "Duplicate trade ID or invalid body")
    })
    public ResponseEntity<TradeResponse> createTrade(@Valid @RequestBody TradeRequest request){
        TradeResponse response = tradeService.create(request);
        return ResponseEntity.created(
                        ServletUriComponentsBuilder.fromCurrentRequest()
                                .path("/{id}")
                                .buildAndExpand(response.getId())
                                .toUri())
                .body(response);
    }

    @GetMapping("/trades/{id}")
    public ResponseEntity<TradeResponse> getTradeById(@PathVariable("id") Long tradeId){
        TradeResponse response = tradeService.getById(tradeId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/trades")
    public ResponseEntity<List<TradeResponse>> getTradesAll(){
        List<TradeResponse> response = tradeService.getAll();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/trades/users/{userID}")
    public ResponseEntity<List<TradeResponse>> getTradesByUser(@PathVariable("userID") Long userId){
        List<TradeResponse> response = tradeService.getByUserId(userId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/trades/stocks/{stockSymbol}")
    public ResponseEntity<List<TradeResponse>> getTradesByStockSymbol(
            @PathVariable (name = "stockSymbol")String stockSymbol,
            @RequestParam(name = "type") String type,
            @RequestParam(name = "start") String startDate,
            @RequestParam(name = "end") String endDate
    ){
        List<TradeResponse> response = tradeService.findBySymbolTypeAndDateRange(stockSymbol, type, startDate, endDate);
        return ResponseEntity.ok(response);
    }

}
