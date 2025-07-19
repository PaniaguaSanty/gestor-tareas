package com.todoproject.demo.config;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class FaviconController {
    @GetMapping("favicon.ico")
    @ResponseBody
    public void returnNoFavicon() {
        // Método vacío para evitar warnings de favicon
    }
}