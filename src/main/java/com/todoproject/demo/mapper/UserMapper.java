package com.todoproject.demo.mapper;

import com.todoproject.demo.dto.request.UserRequestDto;
import com.todoproject.demo.dto.response.UserResponseDto;
import com.todoproject.demo.model.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {

    private final ModelMapper modelMapper;

    public UserMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public UserResponseDto convertToDto(User user) {
        return modelMapper.map(user, UserResponseDto.class);
    }

    public User convertToEntity(UserRequestDto userRequestDto) {
        return modelMapper.map(userRequestDto, User.class);
    }

}
