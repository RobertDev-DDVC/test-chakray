package com.test.chakray.controller;

import com.test.chakray.dto.UserRequestDTO;
import com.test.chakray.dto.UserResponseDTO;
import com.test.chakray.dto.UserUpdateRequestDTO;
import com.test.chakray.mapper.UserMapper;
import com.test.chakray.model.User;
import com.test.chakray.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@Validated
public class UserController {
    private final UserService userService;
    private final String REGEX_UUID = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$";

    public  UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> findAll(
            @RequestParam(required = false) String sortedBy,
            @RequestParam(required = false) String filter
    ) {
        var users = userService.findAllSortedAndFiltered(sortedBy, filter);
        var resp = users.stream().map(UserMapper::toResponse).toList();
        return ResponseEntity.ok(resp);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> get(@PathVariable UUID id) {
        var u = userService.get(id);
        return ResponseEntity.ok(UserMapper.toResponse(u));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UserResponseDTO> create(@Valid @RequestBody UserRequestDTO req) {
        var created = userService.create(req);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(UserMapper.toResponse(created));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserResponseDTO> update(
            @PathVariable @Pattern(regexp = REGEX_UUID, message = "Invalid UUID format") String id,
            @Valid @RequestBody UserUpdateRequestDTO req
    ) {
        var u = userService.update(UUID.fromString(id), req);
        return ResponseEntity.ok(UserMapper.toResponse(u));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
        @PathVariable @Pattern(regexp = REGEX_UUID, message = "Invalid UUID format") String id
    ) {
        userService.delete(UUID.fromString(id));
    }
}
