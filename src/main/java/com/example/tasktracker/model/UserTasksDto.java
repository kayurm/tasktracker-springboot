package com.example.tasktracker.model;

import lombok.Data;

import java.util.List;

@Data
public class UserTasksDto {
    private UserDto user;
    private List<TaskDto> tasks;
}
