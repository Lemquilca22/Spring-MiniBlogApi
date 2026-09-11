package com.miniblog.api.service;

import com.miniblog.api.dto.AuthResponseDto;
import com.miniblog.api.dto.LoginDto;
import com.miniblog.api.dto.RegisterDto;
import com.miniblog.api.model.User;
import com.miniblog.api.repository.UserRepository;
import com.miniblog.api.security.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;

    public String register(RegisterDto registerDto) {
        if (userRepository.existsByUsername(registerDto.getUsername())) {
            throw new RuntimeException("El nombre de usuario ya está registrado.");
        }

        User user = new User();
        user.setUsername(registerDto.getUsername());
        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        userRepository.save(user);
        return "Usuario registrado con éxito";
    }

    public AuthResponseDto login(LoginDto loginDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword())
        );

        String token = jwtUtils.generateToken(loginDto.getUsername());

        return AuthResponseDto.builder()
                .token(token)
                .build();
    }
}