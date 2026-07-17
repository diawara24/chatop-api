package com.openclassroom.chatopapi.controller;

import com.openclassroom.chatopapi.dto.CreateRentalDto;
import com.openclassroom.chatopapi.dto.MessageRequestDto;
import com.openclassroom.chatopapi.dto.RentalDto;
import com.openclassroom.chatopapi.dto.UpdateRentalDto;
import com.openclassroom.chatopapi.record.MessageResponse;
import com.openclassroom.chatopapi.record.RentalUpSertResponse;
import com.openclassroom.chatopapi.services.MessageService;
import com.openclassroom.chatopapi.services.RentalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/messages")
@RequiredArgsConstructor
@Tag(name = "Messages")
public class MessageController {

    private final MessageService messageService;


    @PostMapping()
        @Operation(summary = "Envoyer un message", description = "Envoyer un message concernant une annonce")
        @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Message envoyé",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))),
            @ApiResponse(responseCode = "400", description = "Requête invalide"),
            @ApiResponse(responseCode = "401", description = "Non autorisé")
        })
        public ResponseEntity<MessageResponse> create(@RequestBody MessageRequestDto requestDto) throws Exception {
        return ResponseEntity.status(CREATED).body(messageService.send(requestDto));
        }


}
