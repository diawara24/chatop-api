package com.openclassroom.chatopapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class UpdateRentalDto {

    @NotBlank
    private String name;

    @NotNull
    private Double surface;

    @NotNull
    private BigDecimal price;

    @NotBlank
    private String description;
}
