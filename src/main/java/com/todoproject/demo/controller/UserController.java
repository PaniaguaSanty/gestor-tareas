package com.todoproject.demo.controller;

import com.todoproject.demo.dto.request.UserRequestDto;
import com.todoproject.demo.dto.response.UserResponseDto;
import com.todoproject.demo.service.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {


    private final UserServiceImpl userService;
    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<UserResponseDto> create(@ModelAttribute UserRequestDto REQUEST) {
        logger.info("Entering in create method..");
        UserResponseDto RESPONSE = userService.create(REQUEST);
        logger.info("Exiting create method..");
        return new ResponseEntity<>(RESPONSE, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<UserResponseDto> update(@RequestBody UserRequestDto REQUEST) {
        logger.info("Entering in update method..");
        UserResponseDto RESPONSE = userService.update(REQUEST);
        logger.info("Exiting update method..");
        return new ResponseEntity<>(RESPONSE, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        logger.info("Entering in delete method..");
        userService.delete(String.valueOf(id));
        logger.info("Exiting delete method..");
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<UserResponseDto> findById(@PathVariable Long id) {
        logger.info("Entering in findById method..");
        UserResponseDto RESPONSE = userService.findOne(String.valueOf(id));
        logger.info("Exiting findById method..");
        return new ResponseEntity<>(RESPONSE, HttpStatus.FOUND);
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<UserResponseDto>> findAll() {
        logger.info("Entering in findAll method..");
        List<UserResponseDto> RESPONSES = userService.findAll();
        logger.info("Exiting findAll method..");
        return new ResponseEntity<>(RESPONSES, HttpStatus.FOUND);
    }

    @GetMapping("/form")
    public String showCreateForm(Model model) {
        model.addAttribute("user", new UserRequestDto());
        return "create-user";
    }

    @PostMapping("/submit")
    public String handleForm(@ModelAttribute("user") UserRequestDto user) {
        // Aquí puedes guardar el usuario, validar, etc.
        System.out.println("Name: " + user.getName());
        System.out.println("Email: " + user.getEmail());
        return "redirect:/users/form";
    }
}
