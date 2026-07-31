package com.openclassroom.chatopapi.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
    @Size(max = 2000)
    private String description;

    @NotNull( message = "La photo est obligatoire")
    private MultipartFile picture;

}
