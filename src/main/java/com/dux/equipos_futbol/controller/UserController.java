package com.dux.equipos_futbol.controller;

import com.dux.equipos_futbol.model.Users;
import com.dux.equipos_futbol.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@CrossOrigin
public class UserController {

    private final PasswordEncoder bcrypt;
    private final UserService userService;

    public UserController(PasswordEncoder bcrypt, UserService userService) {
        this.bcrypt = bcrypt;
        this.userService = userService;
    }

    @Operation(summary = "Registrar usuario", description = "Registra un usuario para su autenticacion")
    @PostMapping
    public ResponseEntity<?> save(@RequestBody Users user) {
        user.setPassword(bcrypt.encode(user.getPassword()));
        return ResponseEntity.ok(userService.save(user));
    }
}