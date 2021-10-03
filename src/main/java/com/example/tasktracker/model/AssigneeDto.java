package com.example.tasktracker.model;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AssigneeDto {
    @NotNull
    private Long taskId;
    @NotNull
    private Long assigneeId;
}
