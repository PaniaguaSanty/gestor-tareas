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

    private String code;
    private String tittle;
    private String description;
    private LocalDate dueDate;

}
