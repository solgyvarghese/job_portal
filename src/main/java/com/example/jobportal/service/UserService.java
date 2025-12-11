package com.example.jobportal.service;

import com.example.jobportal.entity.User;
import com.example.jobportal.dto.RegistrationDto;

public interface UserService {
    User registerNewUser(RegistrationDto dto);
    User findByEmail(String email);
}
