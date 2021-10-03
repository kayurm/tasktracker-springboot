package com.example.tasktracker.service;

import com.example.tasktracker.exception.InvalidRequestException;
import com.example.tasktracker.exception.NotFoundException;
import com.example.tasktracker.model.AssigneeDto;
import com.example.tasktracker.model.TaskDto;
import com.example.tasktracker.repository.TaskRepo;
import com.example.tasktracker.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class TaskService {

    private final TaskRepo taskRepo;
    private final UserRepo userRepo;

    @Autowired
    public TaskService(TaskRepo taskRepo, UserRepo userRepo) {
        this.taskRepo = taskRepo;
        this.userRepo = userRepo;
    }

    public TaskDto createTask(TaskDto task) {
        validateAssignee(task);
        validateReporter(task);
        return taskRepo.save(task);
    }

    public TaskDto assignTask(AssigneeDto assignee) {
        validateAssigneeDto(assignee);
        return taskRepo.assign(assignee);
    }

    public Collection<TaskDto> getTasks() {
        return taskRepo.getAll();
    }

    public TaskDto getTask(long taskId) {
        return taskRepo.getById(taskId);
    }

    public TaskDto updateTask(TaskDto task) {
        if (task.getId() == null) throw new InvalidRequestException("Provide id to edit the task");
        return taskRepo.update(task);
    }

    private void validateAssignee(TaskDto task) {
        Long assignee = task.getAssignee();
        if (assignee != null) {
            if (!userRepo.idExists(assignee)) {
                throw new NotFoundException("No such assignee by id: " + assignee);
            }
            if (userRepo.getById(assignee).isDeleted()) {
                throw new InvalidRequestException("Assignee should not be 'deleted'");
            }
        }
    }

    private void validateReporter(TaskDto task) {
        Long reporter = task.getReporter();
        if (!userRepo.idExists(reporter)) {
            throw new NotFoundException("No such reporter by id: " + reporter);
        }
        if (userRepo.getById(task.getReporter()).isDeleted()) {
            throw new InvalidRequestException("Reporter should not be 'deleted'");
        }
    }

    private void validateAssigneeDto(AssigneeDto assignee) {
        Long assigneeId = assignee.getAssigneeId();
        Long taskId = assignee.getAssigneeId();

        if (!taskRepo.idExists(taskId)) {
            throw new NotFoundException("No such task to assign by id: " + assigneeId);
        }
        if (!userRepo.idExists(assigneeId)) {
            throw new NotFoundException("No such assignee by id: " + assigneeId);
        }
        if (userRepo.getById(assigneeId).isDeleted()) {
            throw new InvalidRequestException("Assignee should not be 'deleted'");
        }
    }
}
