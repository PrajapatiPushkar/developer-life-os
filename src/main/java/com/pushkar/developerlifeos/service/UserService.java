package com.pushkar.developerlifeos.service;

import com.pushkar.developerlifeos.dto.LoginRequestDTO;
import com.pushkar.developerlifeos.dto.UserRequestDTO;
import com.pushkar.developerlifeos.entity.Role;
import com.pushkar.developerlifeos.entity.User;
import com.pushkar.developerlifeos.exception.InvalidCredentialsException;
import com.pushkar.developerlifeos.exception.UserNotFoundException;
import com.pushkar.developerlifeos.repository.UserRepository;
import com.pushkar.developerlifeos.security.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtService jwtService) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;

    }

    // ============================
    // Register User
    // ============================

    public User register(UserRequestDTO dto) {

        log.info("Register request received for username: {}", dto.getUsername());

        // Password Match Validation
        if (!dto.getPassword().equals(dto.getConfirmPassword())) {

            throw new RuntimeException("Passwords do not match");

        }

        // Username Validation
        if (userRepository.existsByUsername(dto.getUsername())) {

            throw new RuntimeException("Username already exists");

        }

        // Email Validation
        if (userRepository.existsByEmail(dto.getEmail())) {

            throw new RuntimeException("Email already registered");

        }

        // Phone Validation
        if (userRepository.existsByPhone(dto.getPhone())) {

            throw new RuntimeException("Phone number already registered");

        }

        User user = new User();

        user.setFullName(dto.getFullName());

        user.setEmail(dto.getEmail());

        user.setPhone(dto.getPhone());

        user.setUsername(dto.getUsername());

        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        user.setRole(Role.ROLE_USER);

        user.setEnabled(true);

        User savedUser = userRepository.save(user);

        log.info("User registered successfully: {}", savedUser.getUsername());

        return savedUser;

    }

    // ============================
    // Login User
    // ============================

    public String login(LoginRequestDTO dto) {

        log.info("Login request received for username: {}", dto.getUsername());

        User user = userRepository

                .findByUsername(dto.getUsername())

                .orElseThrow(() ->
                        new UserNotFoundException("User not found"));

        if (!passwordEncoder.matches(
                dto.getPassword(),
                user.getPassword())) {

            throw new InvalidCredentialsException("Invalid credentials");

        }

        log.info("User logged in successfully: {}", user.getUsername());

        return jwtService.generateToken(user);

    }

}