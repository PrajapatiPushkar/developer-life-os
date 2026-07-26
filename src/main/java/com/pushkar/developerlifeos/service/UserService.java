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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private static final Logger log =
            LoggerFactory.getLogger(UserService.class);

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtService jwtService) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public User register(UserRequestDTO dto) {
        log.info("Register request received for username: {}", dto.getUsername());

        User user = new User();

        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole(Role.ROLE_USER);
        log.info("User registered successfully: {}", user.getUsername());

        return userRepository.save(user);
    }

    public String login(LoginRequestDTO dto) {

        log.info("Login request received for username: {}", dto.getUsername());

        User user = userRepository
                .findByUsername(dto.getUsername())
                .orElseThrow(() ->
                        new UserNotFoundException("User Not Found"));

        if (!passwordEncoder.matches(
                dto.getPassword(),
                user.getPassword())) {

            throw new InvalidCredentialsException("Invalid Credentials");
        }

        log.info("User logged in successfully: {}", user.getUsername());
        return jwtService.generateToken(user);
    }
}