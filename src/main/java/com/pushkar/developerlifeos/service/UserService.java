package com.pushkar.developerlifeos.service;

import com.pushkar.developerlifeos.dto.LoginRequestDTO;
import com.pushkar.developerlifeos.dto.UserRequestDTO;
import com.pushkar.developerlifeos.entity.User;
import com.pushkar.developerlifeos.repository.UserRepository;
import com.pushkar.developerlifeos.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
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

    public User register(UserRequestDTO dto){

        User user = new User();

        user.setUsername(dto.getUsername());

        user.setPassword(
                passwordEncoder.encode(dto.getPassword())
        );

        user.setRole("ROLE_USER");

        return userRepository.save(user);
    }

    public String login(LoginRequestDTO dto){

        User user = userRepository
                .findByUsername(dto.getUsername())
                .orElseThrow(
                        () -> new RuntimeException(
                                "User Not Found"));

        if(!passwordEncoder.matches(
                dto.getPassword(),
                user.getPassword())){

            throw new RuntimeException(
                    "Invalid Password");
        }

        return jwtService.generateToken(
                user.getUsername());

    }

}