package com.todoproject.demo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TaskResponseDto {

    private Long id;
    private String code;
    private String title;
    private String description;
    private LocalDate dueDate;
    private String state;   // enum convertido a String para evitar problemas
    private String priority;
    private boolean active;
    private Long assignedUserId;
    private String assignedUserName;
    private String assignedUserAvatarUrl; // Nueva propiedad
    private String projectCode;
    private String projectName;
}
