// src/main/java/com/todoproject/demo/controller/RegistrationController.java
package com.todoproject.demo.controller;

import com.todoproject.demo.model.User;
import com.todoproject.demo.service.RegistrationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class RegistrationController {

    private final RegistrationService service;

    public RegistrationController(RegistrationService service) {
        this.service = service;
    }

    @GetMapping("/register")
    public String showForm() {
        return "register";  // tu Thymeleaf register.html
    }

    @PostMapping("/register")
    public String process(
            @RequestParam String email,
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String name,
            Model model
    ) {
        try {
            User u = service.register(email, username, password, name);
            return "redirect:/login?registrado";
        } catch (IllegalArgumentException ex) {
            model.addAttribute("error", ex.getMessage());
            return "registro";
        }
    }
}
