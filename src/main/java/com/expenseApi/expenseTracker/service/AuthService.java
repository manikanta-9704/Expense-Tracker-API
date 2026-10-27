package com.expenseApi.expenseTracker.service;

import java.util.Locale;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.expenseApi.expenseTracker.dto.AuthResponse;
import com.expenseApi.expenseTracker.dto.LoginRequest;
import com.expenseApi.expenseTracker.dto.RegisterRequest;
import com.expenseApi.expenseTracker.entity.User;
import com.expenseApi.expenseTracker.repository.UserRepo;
import com.expenseApi.expenseTracker.security.JwtService;

@Service
public class AuthService {

    private final UserRepo userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(
            UserRepo userRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Transactional
    public AuthResponse register(RegisterRequest request) {

        String email = request.email()
                .trim()
                .toLowerCase(Locale.ROOT);

        if (userRepository.existsByEmailIgnoreCase(email)) {
            throw new IllegalArgumentException(
                    "An account with this email already exists"
            );
        }

        User user = new User();

        user.setName(request.name().trim());
        user.setEmail(email);

        // Hash the password before saving
        user.setPassword(
                passwordEncoder.encode(request.password())
        );

        User savedUser = userRepository.save(user);

        // Generate a token after successful registration
        String token = jwtService.generateToken(
                savedUser.getEmail()
        );

        return new AuthResponse(
                savedUser.getId(),
                savedUser.getName(),
                savedUser.getEmail(),
                token,
                "Bearer",
                "Registration successful"
        );
    }

    @Transactional(readOnly = true)
    public AuthResponse login(LoginRequest request) {

        String email = request.email()
                .trim()
                .toLowerCase(Locale.ROOT);

        User user = ( userRepository
                .findByEmailIgnoreCase(email))
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Invalid email or password"
                        )
                );

        boolean passwordMatches =
                passwordEncoder.matches(
                        request.password(),
                        user.getPassword()
                );

        if (!passwordMatches) {
            throw new IllegalArgumentException(
                    "Invalid email or password"
            );
        }

        // Generate a token after successful login
        String token = jwtService.generateToken(
                user.getEmail()
        );

        return new AuthResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                token,
                "Bearer",
                "Login successful"
        );
    }
}