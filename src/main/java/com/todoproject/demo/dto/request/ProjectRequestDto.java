package com.todoproject.demo.dto.request;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectRequestDto {

    private String code;
    private String name;
    private LocalDate startDate;
    private LocalDate finishDate;
    private String description;
    private boolean active;

    // ID del equipo al que pertenece el proyecto
    private String teamDni;
}
