package com.dvtsoftware.stocktrade.dto.request;

import com.dvtsoftware.stocktrade.dto.UserDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;

@Data
@ToString
public class TradeRequest {

    @NotNull(message = "id is required")
    @Positive(message = "id must be positive")
    private Long id;

    @NotBlank(message = "type is required")
    private String type;

    @Valid
    @NotNull(message = "user is required")
    private UserDto user;

    @NotBlank(message = "symbol is required")
    private String symbol;

    @NotNull(message = "shares is required")
    @Positive(message = "shares must be positive")
    private Integer shares;

    @NotNull(message = "price is required")
    @DecimalMin(value = "0.01", message = "price must be positive")
    private BigDecimal price;

    @NotBlank(message = "timestamp is required")
    private String timestamp;
}
