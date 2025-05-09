package com.todoproject.demo.controller;

import com.todoproject.demo.dto.request.UserRequestDto;
import com.todoproject.demo.dto.response.UserResponseDto;
import com.todoproject.demo.service.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;

@Controller
public class UserController implements CrudController<UserRequestDto, UserResponseDto> {


    private final UserServiceImpl userService;
    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @Override
    @PostMapping("/create")
    public ResponseEntity<UserResponseDto> create(UserRequestDto REQUEST) {
        logger.info("Entering in create method..");
        UserResponseDto RESPONSE = userService.create(REQUEST);
        logger.info("Exiting create method..");
        return new ResponseEntity<>(RESPONSE, HttpStatus.CREATED);
    }

    @Override
    @PutMapping("/update/{id}")
    public ResponseEntity<UserResponseDto> update(UserRequestDto REQUEST) {
        logger.info("Entering in update method..");
        UserResponseDto RESPONSE = userService.update(REQUEST);
        logger.info("Exiting update method..");
        return new ResponseEntity<>(RESPONSE, HttpStatus.OK);
    }

    @Override
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(Long id) {
        logger.info("Entering in delete method..");
        userService.delete(String.valueOf(id));
        logger.info("Exiting delete method..");
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    @GetMapping("/find/{id}")
    public ResponseEntity<UserResponseDto> findById(Long id) {
        logger.info("Entering in findById method..");
        UserResponseDto RESPONSE = userService.findOne(String.valueOf(id));
        logger.info("Exiting findById method..");
        return new ResponseEntity<>(RESPONSE, HttpStatus.FOUND);
    }

    @Override
    @GetMapping("/findAll")
    public ResponseEntity<List<UserResponseDto>> findAll() {
        logger.info("Entering in findAll method..");
        List<UserResponseDto> RESPONSES = userService.findAll();
        logger.info("Exiting findAll method..");
        return new ResponseEntity<>(RESPONSES, HttpStatus.FOUND);
    }
}
