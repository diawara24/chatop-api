package com.openclassroom.chatopapi.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class CreateRentalDto {

    @NotBlank
    private String name;

    @NotNull
    private Double surface;

    @NotNull
    private BigDecimal price;

    @NotBlank
    private String description;

    private MultipartFile picture;

}
