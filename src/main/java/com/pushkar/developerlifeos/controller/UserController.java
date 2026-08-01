package com.pushkar.developerlifeos.controller;

import com.pushkar.developerlifeos.dto.LoginRequestDTO;
import com.pushkar.developerlifeos.dto.LoginResponseDTO;
import com.pushkar.developerlifeos.dto.UserRequestDTO;
import com.pushkar.developerlifeos.entity.User;
import com.pushkar.developerlifeos.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(
            @RequestBody UserRequestDTO dto){

        return ResponseEntity.ok(
                userService.register(dto)
        );
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(

            @RequestBody LoginRequestDTO dto){

        String token =
                userService.login(dto);

        return ResponseEntity.ok(
                new LoginResponseDTO(token));

    }

}