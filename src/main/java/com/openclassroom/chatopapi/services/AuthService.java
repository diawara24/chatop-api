package com.openclassroom.chatopapi.services;

import com.openclassroom.chatopapi.dto.AuthRequest;
import com.openclassroom.chatopapi.dto.RegisterUserDto;
import com.openclassroom.chatopapi.dto.UserDto;
import com.openclassroom.chatopapi.model.User;
import com.openclassroom.chatopapi.record.AuthResponse;
import com.openclassroom.chatopapi.utils.JWTTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;

    private final UserDetailsService userDetailsService;

    private final AuthenticationManager authenticationManager;

    private final JWTTokenProvider jwtTokenProvider;

    public AuthResponse register(RegisterUserDto dto) {
        User user = User.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .build();

        User saved = userService.save(user);

        UserDetails userDetails = userDetailsService.loadUserByUsername(saved.getEmail());

        return new AuthResponse(jwtTokenProvider.generateToken(userDetails));
    }

    public AuthResponse login(AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
        String token = jwtTokenProvider.generateToken(userDetails);
        return new AuthResponse(token);
    }

}
