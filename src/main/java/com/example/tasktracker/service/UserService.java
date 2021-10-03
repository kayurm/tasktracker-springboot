package com.example.tasktracker.service;

import com.example.tasktracker.exception.InvalidRequestException;
import com.example.tasktracker.exception.NotFoundException;
import com.example.tasktracker.model.TaskDto;
import com.example.tasktracker.model.UserDto;
import com.example.tasktracker.model.UserTasksDto;
import com.example.tasktracker.repository.TaskRepo;
import com.example.tasktracker.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepo userRepo;
    private final TaskRepo taskRepo;
    private final String noSuchIdMsg = "User id does not exist";

    @Autowired
    public UserService(UserRepo userRepo, TaskRepo taskRepo) {
        this.userRepo = userRepo;
        this.taskRepo = taskRepo;
    }

    public List<UserDto> getUsers() {
        return userRepo.getAllNotDeleted();
    }

    public UserTasksDto getUserAndTheirTasks(int userId) {
        UserDto user = userRepo.getById(userId);
        if (user.isDeleted()) throw new InvalidRequestException("requested user is deleted");

        List<TaskDto> tasks = taskRepo.getTasksByAssignee(userId);
        UserTasksDto userTasksDto = new UserTasksDto();
        userTasksDto.setUser(user);
        userTasksDto.setTasks(tasks);
        return userTasksDto;
    }

    public UserDto createUser(UserDto user) {
        if (userRepo.emailExists(user.getEmail())) throw new InvalidRequestException("Email should be unique");
        return userRepo.save(user);
    }

    public UserDto updateUser(UserDto user) {
        if (user.getId()==null) throw new InvalidRequestException("Provide id to edit the user");
        if (!userRepo.idExists(user.getId())) throw new NotFoundException(noSuchIdMsg);
        return userRepo.update(user);
    }

    public String deleteUser(long userId) {
        if (!userRepo.idExists(userId)) throw new NotFoundException(noSuchIdMsg);
        return userRepo.delete(userId);
    }
}
