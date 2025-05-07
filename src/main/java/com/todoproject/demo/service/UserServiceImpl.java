package com.todoproject.demo.service;

import com.todoproject.demo.dto.request.UserRequestDto;
import com.todoproject.demo.dto.response.UserResponseDto;
import com.todoproject.demo.exception.NotFoundException;
import com.todoproject.demo.mapper.UserMapper;
import com.todoproject.demo.model.User;
import com.todoproject.demo.repository.UserRepository;
import com.todoproject.demo.util.CRUD;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements CRUD<UserResponseDto, UserRequestDto> {

    private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserResponseDto create(UserRequestDto userRequestDto) {
        try {
            logger.info("Entering in create user Method...");
            User newUser = userMapper.convertToEntity(userRequestDto);
            User savedUser = userRepository.save(newUser);
            return userMapper.convertToDto(savedUser);
        } catch (NotFoundException e) {
            throw new NotFoundException("Error while creating user..."); //cambiar tipo de excepción.
        }
    }

    @Override
    public UserResponseDto update(UserRequestDto userRequestDto) {
        try {
            logger.info("Entering in update user method...");
            User existingUser = userRepository.findByDni(userRequestDto.getDni())
                    .orElseThrow(() -> new NotFoundException("Error, user not found..."));
            existingUser.setName(userRequestDto.getName());
            existingUser.setEmail(userRequestDto.getEmail());
            // existingUser.setTasks();
            // existingUser.setTeam();
            // existingUser.setComments();
            User savedUser = userRepository.save(existingUser);
            return userMapper.convertToDto(savedUser);
        } catch (NotFoundException e) {
            throw new NotFoundException("Error, while updating user..."); //cambiar tipo de excepción.
        }
    }

    @Override
    public void delete(String id) {
        logger.info("Entering in delete method...");
        User user = userRepository.findById(Long.parseLong(id))
                .orElseThrow(() -> new NotFoundException("User not found.."));
        userRepository.delete(user);
    }

    @Override
    public List<UserResponseDto> findAll() {
        logger.info("Entering in findAll method...");
        List<User> users = userRepository.findAll();

        return users.stream()
                .map(userMapper::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserResponseDto findOne(String id) {
        logger.info("Entering in findOne method...");
        User user = userRepository.findById(Long.parseLong(id))
                .orElseThrow(() -> new NotFoundException("User not found.."));
        return userMapper.convertToDto(user);
    }

    @Override
    public UserResponseDto disable(String id) {
        logger.info("Entering in disable user method...");
        User user = userRepository.findById(Long.parseLong(id))
                .orElseThrow(() -> new NotFoundException("User not found.."));
        user.setActive(false);
        userRepository.save(user);
        return userMapper.convertToDto(user);
    }
}
