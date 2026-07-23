package com.pushkar.developerlifeos.service;

import com.pushkar.developerlifeos.dto.UserRequestDTO;
import com.pushkar.developerlifeos.entity.User;
import com.pushkar.developerlifeos.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
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

}