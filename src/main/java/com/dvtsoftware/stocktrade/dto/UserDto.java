package com.dvtsoftware.stocktrade.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    @NotNull(message = "user.id is required")
    private Long id;

    @NotBlank(message = "user.name is required")
    private String name;
}
