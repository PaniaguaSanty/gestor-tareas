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
public class UserResponseDto {

    private String name;
    private String email;
    private String dni;
    private String avatarUrl;
    private String username;
    private String password;
    private boolean active;
    private List<User> users;

}
