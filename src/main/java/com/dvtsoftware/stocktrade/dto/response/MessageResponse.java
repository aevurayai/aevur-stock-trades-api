package com.dvtsoftware.stocktrade.dto.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"message"})
public record MessageResponse(String message) {
}
