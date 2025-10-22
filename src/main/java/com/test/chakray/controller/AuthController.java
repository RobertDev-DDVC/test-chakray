package com.test.chakray.controller;

import com.test.chakray.dto.LoginRequestDTO;
import com.test.chakray.dto.LoginResponseDTO;
import com.test.chakray.model.User;
import com.test.chakray.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class AuthController {
    private final UserService service;

    public AuthController(UserService service) {
        this.service = service;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO req) {
        User u = service.login(req.getUsername(), req.getPassword());
        return ResponseEntity.ok(
                new LoginResponseDTO("ok", u.getId().toString(), u.getTaxId(), u.getName(), u.getEmail())
        );
    }
}
