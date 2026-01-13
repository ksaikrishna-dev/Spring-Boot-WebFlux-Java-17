package com.webflux.controller;

import com.webflux.model.User;
import com.webflux.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public Flux<User> getUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public Mono<User> getUseser(@PathVariable String id) {
        return userService.getUserById(id);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public Mono<User> createUser(@Valid @RequestBody User user) {
        return userService.createUser(user);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public Mono<Void> deleteUser(@PathVariable String id) {
        return userService.deleteUser(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public Mono<User> update(@PathVariable String id, @Valid @RequestBody User user) {
        return userService.updateUser(id, user);
    }
}
