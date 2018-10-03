package be.nexios.projectBootcamp.controller;

import be.nexios.projectBootcamp.exception.BadRequestException;
import be.nexios.projectBootcamp.exception.NotFoundException;
import be.nexios.projectBootcamp.service.UserService;
import be.nexios.projectBootcamp.service.dto.UserDTO;
import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.net.URI;

public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/api/users")
    public Mono<ResponseEntity<Void>> createUser(@Valid @RequestBody UserDTO userDTO) {
        return this.userService.createUser(userDTO)
                .map(id -> ResponseEntity
                    .created(URI.create("api/users"+id))
                    .build());
    }

    @GetMapping("/api/users/{id}")
    public Mono<UserDTO> getUser(@PathVariable("id")ObjectId id) {
        return this.userService.getUser(id);
    }

    @GetMapping("/api/users/")
    public Flux<UserDTO> getAllUsers() {
        return this.userService.getAllUsers();
    }

    @PutMapping("/api/users/{id}")
    public Mono<Void> updateUser(@PathVariable("id") ObjectId id,
                                 @Valid @RequestBody UserDTO userDTO) {
        return this.userService.updateUser(id, userDTO);
    }

    @DeleteMapping("/api/users/{id}")
    public Mono<Void> deleteUser(@PathVariable("id") ObjectId id) {
        return this.userService.deleteUser(id);
    }
    @ExceptionHandler
    public ResponseEntity handleNotFoundException(NotFoundException e) {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler
    public ResponseEntity handleBadRequestException(BadRequestException e) {
        return ResponseEntity.badRequest().build();
    }

}
