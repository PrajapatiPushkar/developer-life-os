package com.pushkar.developerlifeos.config;

import com.pushkar.developerlifeos.entity.Role;
import com.pushkar.developerlifeos.entity.User;
import com.pushkar.developerlifeos.repository.UserRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository,
                           PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {

        if (userRepository.findByUsername("admin").isEmpty()) {

            User admin = new User();

            admin.setUsername("admin");

            admin.setPassword(
                    passwordEncoder.encode("admin123")
            );

            admin.setRole(Role.ROLE_ADMIN);

            userRepository.save(admin);

            System.out.println("Default Admin Created.");
        }
    }
}