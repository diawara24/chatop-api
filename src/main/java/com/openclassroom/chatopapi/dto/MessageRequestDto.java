package com.openclassroom.chatopapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class MessageRequestDto {
    private String message;

    @JsonProperty("rental_id")
    private Integer rentalId;

    @JsonProperty("user_id")
    private Integer userId;
}
