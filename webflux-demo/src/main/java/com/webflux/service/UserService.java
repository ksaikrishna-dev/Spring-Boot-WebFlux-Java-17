package com.webflux.service;

import com.webflux.exception.UserNotFoundException;
import com.webflux.model.User;
import com.webflux.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public Flux<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Mono<User> getUserById(String id) {
        return userRepository.findById(id)
                .switchIfEmpty(Mono.error(new UserNotFoundException("User not found with id: " + id)));
    }

    public Mono<User> createUser(User user) {
        return userRepository.save(user);
    }

    public Mono<Void> deleteUser(String id) {
        return userRepository.findById(id)
                .switchIfEmpty(Mono.error(new UserNotFoundException("User not found with id: " + id)))
                .flatMap(existingUser -> userRepository.delete(existingUser));
    }

    public Mono<User> updateUser(String id, User user) {
        return userRepository.findById(id)
                .flatMap(existing -> {
                    existing.setName(user.getName());
                    existing.setEmail(user.getEmail());
                    return userRepository.save(existing);
                });
    }
}
