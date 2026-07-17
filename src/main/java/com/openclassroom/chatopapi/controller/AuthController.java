package com.openclassroom.chatopapi.controller;


import com.openclassroom.chatopapi.dto.AuthRequest;
import com.openclassroom.chatopapi.dto.RegisterUserDto;
import com.openclassroom.chatopapi.dto.UserDto;
import com.openclassroom.chatopapi.record.AuthResponse;
import com.openclassroom.chatopapi.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register( @RequestBody RegisterUserDto request){
        return ResponseEntity.ok(
                authService.register(request)
        );
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request){
        return ResponseEntity.ok(
                authService.login(request)
        );
    }

    @GetMapping("/me")
    public ResponseEntity<UserDto> getLoggedUser(){
        return ResponseEntity.ok(authService.getLoggedUser());
    }
}
