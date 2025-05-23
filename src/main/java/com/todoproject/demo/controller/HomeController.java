package com.todoproject.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {


    //este método está en userController, lo separé porque probablemente configuraré bastantes cosas en la página principal
    //si a futuro no termino configurando nada, corresponde borrarla..
    @GetMapping("/")
    public String showHomePage() {
        return "home";
    }

    //clase dejada para posibles configuraciones futuras:
}