package com.example.tasktracker.controller;

import com.example.tasktracker.model.UserDto;
import com.example.tasktracker.model.UserTasksDto;
import com.example.tasktracker.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping("")
    public List<UserDto> getAllUsers() {
        return service.getUsers();
    }

    @GetMapping("/{userId}")
    public UserTasksDto getUserAndTheirTasks(@PathVariable int userId) {
        return service.getUserAndTheirTasks(userId);
    }

    @PostMapping()
    public UserDto createUser(@Valid @RequestBody UserDto user) {
        return service.createUser(user);
    }

    @PutMapping()
    public UserDto updateUser(@Valid @RequestBody UserDto user) {
        return service.updateUser(user);
    }

    @DeleteMapping("/{userId}")
    public String deleteUser(@PathVariable long userId) {
        return service.deleteUser(userId);
    }

}
