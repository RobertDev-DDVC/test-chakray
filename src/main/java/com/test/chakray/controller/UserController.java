package com.test.chakray.controller;

import com.test.chakray.dto.UserRequestDTO;
import com.test.chakray.dto.UserUpdateRequestDTO;
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
    public ResponseEntity<List<User>> findAll(
            @RequestParam(required = false) String sortedBy,
            @RequestParam(required = false) String filter
    ) {
        List<User> result = userService.findAllSortedAndFiltered(sortedBy, filter);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
     public User get(
        @PathVariable @Pattern(regexp = REGEX_UUID, message = "Invalid UUID format") String id
    ) {
        return userService.get(UUID.fromString(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User create(@Valid @RequestBody UserRequestDTO req) {
        return userService.create(req);
    }

    @PatchMapping("/{id}")
    public User update(
            @PathVariable @Pattern(regexp = REGEX_UUID, message = "Invalid UUID format") String id,
            @Valid @RequestBody UserUpdateRequestDTO req
    ) {
        return userService.update(UUID.fromString(id), req);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
        @PathVariable @Pattern(regexp = REGEX_UUID, message = "Invalid UUID format") String id
    ) {
        userService.delete(UUID.fromString(id));
    }
}
