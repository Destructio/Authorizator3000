package com.github.destructio.authorizator3000.controller;

import com.github.destructio.authorizator3000.model.dto.UserDto;
import com.github.destructio.authorizator3000.model.entity.User;
import com.github.destructio.authorizator3000.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public User createUser(@RequestBody UserDto userDto) {
        return userService.createUser(userDto);
    }

    @GetMapping(path = "/{id}")
    public User getUser(@PathVariable UUID id) {
        return userService.getUser(id);
    }

    @PatchMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void updateUser(@PathVariable UUID id, UserDto userDto) {
        userService.updateUser(id, userDto);
    }

    @DeleteMapping(path = "/{id}")
    public void deleteUser(@PathVariable UUID id) {
        userService.deleteUser(id);
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }
}
