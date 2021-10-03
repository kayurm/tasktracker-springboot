package com.example.tasktracker.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class UserDto {
    private Long id;
    @NotBlank
    private String name;
    @Email
    @NotNull
    private String email;
    @JsonIgnore
    private boolean deleted;
}
