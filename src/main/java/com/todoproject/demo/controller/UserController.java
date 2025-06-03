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
    public ResponseEntity<UserResponseDto> create(@RequestBody UserRequestDto REQUEST) {
        logger.info("Entering in create method..");
        UserResponseDto RESPONSE = userService.create(REQUEST);
        logger.info("Exiting create method..");
        return new ResponseEntity<>(RESPONSE, HttpStatus.CREATED);
    }

    @PutMapping("/update/{dni}")
    public ResponseEntity<UserResponseDto> update(@RequestBody UserRequestDto REQUEST) {
        logger.info("Entering in update method..");
        UserResponseDto RESPONSE = userService.update(REQUEST);
        logger.info("Exiting update method..");
        return new ResponseEntity<>(RESPONSE, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{dni}")
    public ResponseEntity<Void> delete(@PathVariable String dni) {
        logger.info("Entering in delete method..");
        userService.delete(dni);
        logger.info("Exiting delete method..");
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/find/{dni}")
    public ResponseEntity<UserResponseDto> findById(@PathVariable String dni) {
        logger.info("Entering in findById method..");
        UserResponseDto RESPONSE = userService.findOne(dni);
        logger.info("Exiting findById method..");
        return new ResponseEntity<>(RESPONSE, HttpStatus.FOUND);
    }

    //url para probar el método: http://localhost:8080/users/list
    @GetMapping("/list")
    public String findAllUsers(Model model) {
        logger.info("Entering in findAll method..");
        List<UserResponseDto> users = userService.findAll();
        model.addAttribute("users", users);
        logger.info("Exiting findAll method..");
        return "userManagement";
    }

    //url para probar el método: http://localhost:8080/users/form
    @GetMapping("/form")
    public String showCreateForm(Model model) {
        model.addAttribute("user", new UserRequestDto());
        return "create-user";
    }

    @PostMapping("/save")
    public String handleForm(@ModelAttribute("user") UserRequestDto user) {
        userService.create(user);
        return "redirect:/users/list"; //hacer que redireccione a "create-user-success con pantalla de carga".
    }

    /**
     * Muestra el formulario para editar un usuario
     */
    @GetMapping("/update/{dni}")
    public String showUpdateForm(@PathVariable String dni, Model model) {
        // 1) Recupera el usuario existente
        UserResponseDto existing = userService.findOne(dni);
        // 2) Mapea a RequestDto para el form
        UserRequestDto formDto = new UserRequestDto();
        formDto.setName(existing.getName());
        formDto.setEmail(existing.getEmail());
        formDto.setDni(existing.getDni());
        formDto.setActive(existing.isActive());
        model.addAttribute("user", formDto);
        // 3) Retorna la plantilla Thymeleaf
        return "update-user";
    }

    /**
     * Procesa el submit del formulario de edición
     */
    @PostMapping("/update")
    public String handleUpdate(@ModelAttribute("user") UserRequestDto user) {
        userService.update(user);
        return "redirect:/users/list";
    }

    /**
     * Borra el usuario y redirige
     */
    @GetMapping("/delete/{dni}")
    public String deleteUser(@PathVariable String dni) {
        userService.delete(dni);
        return "redirect:/users/list";
    }

    @GetMapping("/disable/{dni}")
    public String disableUser(@PathVariable String dni) {
        logger.info("Entering disableUser method for DNI: {}", dni);
        userService.disable(dni);
        return "redirect:/users/list";
    }

    @GetMapping("/enableUser/{dni}")
    public String enableUser(@PathVariable String dni) {
        logger.info("Entering enableUser method for DNI: {}", dni);
        userService.enableUser(dni);
        return "redirect:/users/list";
    }

    @GetMapping("/searchBar")
    public String searchUsers(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Boolean status,
            Model model
    ) {
        // Asigna true por defecto si status es null
        boolean statusValue = (status != null) ? status : true;
        List<UserResponseDto> users = userService.search(search, statusValue);

        model.addAttribute("users", users);
        model.addAttribute("paramSearch", search);
        model.addAttribute("paramStatus", status);
        return "userManagement";
    }

    //TODO: LOS ROOTS TIENEN QUE IR EN UN CONTROLLER A PARTE (PUEDE SER HOMECONTROLLER U OTRO)
    @GetMapping("/")
    public String home() {
        return "home";
    }

    //login de users. TODO: IMPLEMENTACIÓN
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/mainPage")
    public String mainPage() {
        return "mainPage";
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard";
    }
}
