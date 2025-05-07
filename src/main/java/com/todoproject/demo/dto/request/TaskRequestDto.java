package com.todoproject.demo.dto.request;

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
    private String tittle;
    private String description;
    private LocalDate dueDate;

    //TODO: CREAR OTROS DTO´S PARA ASIGNAR UN TASK A UN USER, Y UN TASK A UN PROJECT, COMMENT, ETC..
}
