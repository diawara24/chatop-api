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

import java.util.List;

@RestController
@RequestMapping("/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;


    @PostMapping()
    public ResponseEntity<MessageResponse> create(@RequestBody MessageRequestDto requestDto) throws Exception {
        return ResponseEntity.ok(messageService.send(requestDto));
    }


}
