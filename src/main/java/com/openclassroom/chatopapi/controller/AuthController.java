package com.openclassroom.chatopapi.controller;


import com.openclassroom.chatopapi.dto.AuthRequest;
import com.openclassroom.chatopapi.dto.RegisterUserDto;
import com.openclassroom.chatopapi.dto.UserDto;
import com.openclassroom.chatopapi.record.AuthResponse;
import com.openclassroom.chatopapi.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Authentification")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    @Operation(summary = "Enregistrer un nouvel utilisateur", description = "Créer un nouvel utilisateur et retourner un token d'authentification")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Utilisateur enregistré",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthResponse.class))),
        @ApiResponse(responseCode = "400", description = "Requête invalide")
    })
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterUserDto request){
    return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    @Operation(summary = "Authentifier un utilisateur", description = "S'authentifier avec l'email et le mot de passe et recevoir un token JWT")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Authentifié",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthResponse.class))),
        @ApiResponse(responseCode = "401", description = "Identifiants invalides")
    })
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request){
    return ResponseEntity.ok(authService.login(request));
    }

    @GetMapping("/me")
    @Operation(summary = "Récupérer l'utilisateur courant", description = "Retourne les informations de l'utilisateur actuellement authentifié")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Utilisateur courant",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class))),
        @ApiResponse(responseCode = "401", description = "Non autorisé")
    })
    public ResponseEntity<UserDto> getLoggedUser(){
        return ResponseEntity.ok(authService.getLoggedUser());
        }
}
