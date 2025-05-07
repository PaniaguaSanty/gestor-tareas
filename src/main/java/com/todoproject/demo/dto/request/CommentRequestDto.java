package com.todoproject.demo.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentRequestDto {

    private String content;
    private LocalDateTime createdAt;
    //user request {autor que crea el comentario}
    //task request {El comentario debe ser asignado a una tarea en específico}
}
