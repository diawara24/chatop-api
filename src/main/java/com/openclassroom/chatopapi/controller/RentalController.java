package com.openclassroom.chatopapi.controller;

import com.openclassroom.chatopapi.dto.CreateRentalDto;
import com.openclassroom.chatopapi.dto.RentalDto;
import com.openclassroom.chatopapi.dto.RentalListResponse;
import com.openclassroom.chatopapi.dto.UpdateRentalDto;
import com.openclassroom.chatopapi.record.RentalUpSertResponse;
import com.openclassroom.chatopapi.services.RentalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rentals")
@RequiredArgsConstructor
public class RentalController {

    private final RentalService rentalService;

    @GetMapping()
    public ResponseEntity<RentalListResponse> getAll() {
        return ResponseEntity.ok(rentalService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RentalDto> getById(@PathVariable(name = "id") Integer id) {
        return ResponseEntity.ok(rentalService.findById(id));
    }

    @PostMapping(
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<RentalUpSertResponse> create(@Valid @ModelAttribute CreateRentalDto rentalDto) throws Exception {

        return ResponseEntity.ok(rentalService.create(rentalDto));
    }

    @PutMapping(
            value = "/{id}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<RentalUpSertResponse> update(@PathVariable Integer id, @Valid @ModelAttribute UpdateRentalDto dto) {
        return ResponseEntity.ok(rentalService.update(id, dto));
    }



}
