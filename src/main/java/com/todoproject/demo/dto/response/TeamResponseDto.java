package com.todoproject.demo.dto.response;

import com.todoproject.demo.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TeamResponseDto {

    private String name;
    private boolean active;
    private List<User> users;
}
