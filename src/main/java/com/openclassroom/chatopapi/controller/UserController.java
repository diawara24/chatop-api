package com.openclassroom.chatopapi.controller;

import com.openclassroom.chatopapi.dto.UserDto;
import com.openclassroom.chatopapi.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.Parameter;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Tag(name = "Utilisateurs")
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
        @Operation(summary = "Récupérer un utilisateur par id", description = "Récupérer les informations d'un utilisateur par son id")
        @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Utilisateur trouvé",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class))),
            @ApiResponse(responseCode = "401", description = "Non autorisé")
        })
        public ResponseEntity<UserDto> getUserById(@Parameter(description = "ID de l'utilisateur", required = true) @PathVariable(name = "id") Integer id){
        return ResponseEntity.ok(userService.findById(id));
        }
}
