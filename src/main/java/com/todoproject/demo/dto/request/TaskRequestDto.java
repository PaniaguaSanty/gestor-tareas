package com.todoproject.demo.dto.request;

import com.todoproject.demo.model.Priority;
import com.todoproject.demo.model.TaskState;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TaskRequestDto {

    private String code;
    private String title;
    private String description;
    private LocalDate dueDate;
    private String state;    // enum enviado como String, ej. "TODO", "IN_PROGRESS"
    private String priority; // enum como String, ej. "HIGH"
    private Long userId;


}
