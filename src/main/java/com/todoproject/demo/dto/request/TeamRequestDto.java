package com.todoproject.demo.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TeamRequestDto {

    private String dni;
    private String name;
    private String description;
    private boolean active;
    private List<UserRequestDto> users;
    private List<ProjectRequestDto> projects;
}
