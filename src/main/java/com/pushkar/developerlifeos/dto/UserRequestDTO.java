package com.pushkar.developerlifeos.dto;

import lombok.Data;

@Data
public class UserRequestDTO {

    private String fullName;

    private String email;

    private String phone;

    private String username;

    private String password;

    private String confirmPassword;

}