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
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.Parameter;

import java.util.List;

@RestController
@RequestMapping("/rentals")
@RequiredArgsConstructor
@Tag(name = "Locations")
public class RentalController {

    private final RentalService rentalService;

    @GetMapping()
    @Operation(summary = "Récupérer toutes les annonces", description = "Récupérer la liste des annonces")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Liste des annonces",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = RentalListResponse.class))),
            @ApiResponse(responseCode = "401", description = "Non autorisé")
    })
    public ResponseEntity<RentalListResponse> getAll() {
        return ResponseEntity.ok(rentalService.getAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Récupérer une annonce par id", description = "Récupérer une annonce par son id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Annonce trouvée",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = RentalDto.class))),
            @ApiResponse(responseCode = "401", description = "Non autorisé")
    })
    public ResponseEntity<RentalDto> getById(@Parameter(description = "ID de l'annonce", required = true) @PathVariable(name = "id") Integer id) {
        return ResponseEntity.ok(rentalService.findById(id));
    }

    @PostMapping(
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Créer une annonce", description = "Créer une nouvelle annonce avec image (multipart/form-data)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Annonce créée",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = RentalUpSertResponse.class))),
            @ApiResponse(responseCode = "400", description = "Requête invalide"),
            @ApiResponse(responseCode = "401", description = "Non autorisé")
    })
    public ResponseEntity<RentalUpSertResponse> create(@Valid @ModelAttribute CreateRentalDto rentalDto) throws Exception {

        return ResponseEntity.ok(rentalService.create(rentalDto));
    }

    @PutMapping(
            value = "/{id}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Mettre à jour une annonce", description = "Mettre à jour une annonce existante par id (multipart/form-data)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Annonce mise à jour",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = RentalUpSertResponse.class))),
            @ApiResponse(responseCode = "400", description = "Requête invalide"),
            @ApiResponse(responseCode = "401", description = "Non autorisé")
    })
    public ResponseEntity<RentalUpSertResponse> update(@Parameter(description = "ID de l'annonce à mettre à jour", required = true) @PathVariable Integer id, @Valid @ModelAttribute UpdateRentalDto dto) {
        return ResponseEntity.ok(rentalService.update(id, dto));
    }



}
