package com.example.tasktracker.repository;

import com.example.tasktracker.model.AssigneeDto;
import com.example.tasktracker.model.TaskDto;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
public class TaskRepo {

    private final Map<Long, TaskDto> tasksMap = new HashMap<>();
    private final AtomicLong counter = new AtomicLong();

    public TaskDto getById(long id) {
        return tasksMap.get(id);
    }

    public List<TaskDto> getAll() {
        return new ArrayList<>(tasksMap.values());
    }

    public List<TaskDto> getTasksByAssignee(long userId) {
        return tasksMap.values().stream()
                .filter(t -> userId == t.getAssignee())
                .collect(Collectors.toList());
    }

    public TaskDto save(TaskDto task) {
        long assignedId = counter.incrementAndGet();
        task.setId(assignedId);
        tasksMap.put(assignedId, task);
        return task;
    }

    public TaskDto update(TaskDto task) {
        long id = task.getId();
        TaskDto updatedTask = tasksMap.get(id);
        updatedTask.setTitle(task.getTitle());
        updatedTask.setDescription(task.getDescription());
        tasksMap.replace(id, updatedTask);
        return updatedTask;
    }

    public TaskDto assign(AssigneeDto assignee) {
        TaskDto task = tasksMap.get(assignee.getTaskId());
        task.setAssignee(assignee.getAssigneeId());
        return task;
    }

    public boolean idExists(long id) {
        return tasksMap.containsKey(id);
    }
}
