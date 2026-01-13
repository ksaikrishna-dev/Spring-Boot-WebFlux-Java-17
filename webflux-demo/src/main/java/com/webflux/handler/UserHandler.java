package com.webflux.handler;

import com.webflux.model.User;
import com.webflux.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class UserHandler {


    private final UserService userService;


    public Mono<ServerResponse> getUsers(ServerRequest request) {
        return ServerResponse.ok().body(userService.getAllUsers(), User.class);
    }


    public Mono<ServerResponse> getUser(ServerRequest request) {
        return userService.getUserById(request.pathVariable("id"))
                .flatMap(user -> ServerResponse.ok().bodyValue(user));
    }


    public Mono<ServerResponse> createUser(ServerRequest request) {
        return request.bodyToMono(User.class)
                .flatMap(userService::createUser)
                .flatMap(user -> ServerResponse.status(HttpStatus.CREATED).bodyValue(user));
    }
}
