package com.todoproject.demo.dto.response;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectResponseDto {

    private Long id;
    private String code;
    private String name;
    private LocalDate startDate;
    private LocalDate finishDate;
    private String description;
    private boolean active;

    // Info básica del equipo (solo el nombre, pero se puede extender)
    private String teamDni;
    private String teamName;
    private List<TaskResponseDto> tasks;
}
