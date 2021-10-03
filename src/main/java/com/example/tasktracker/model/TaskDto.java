package com.example.tasktracker.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class TaskDto {
    private Long id;
    @NotBlank
    private String title;
    @NotBlank
    private String description;
    @NotNull
    private Long reporter;
    private Long assignee;
}
