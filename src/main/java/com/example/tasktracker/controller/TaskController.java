package com.example.tasktracker.controller;

import com.example.tasktracker.model.AssigneeDto;
import com.example.tasktracker.model.TaskDto;
import com.example.tasktracker.service.TaskService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService service;

    public TaskController(TaskService service) {
        this.service = service;
    }

    @GetMapping()
    public Collection<TaskDto> getAllTasks() {
        return service.getTasks();
    }

    @GetMapping("/{taskId}")
    public TaskDto getTask(@PathVariable long taskId) {
        return service.getTask(taskId);
    }

    @PostMapping()
    public TaskDto createTask(@Valid @RequestBody TaskDto task) {
        return service.createTask(task);
    }

    @PostMapping("/assign")
    public TaskDto assignTask(@Valid @RequestBody AssigneeDto assignee) {
        return service.assignTask(assignee);
    }

    @PutMapping()
    public TaskDto updateTask(@Valid @RequestBody TaskDto task) {
        return service.updateTask(task);
    }
}
