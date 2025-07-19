package com.todoproject.demo.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String processLogin(@RequestParam String token,
                               HttpServletResponse response,
                               RedirectAttributes redirectAttributes) {

        if ("TEAM123".equals(token)) {
            // Establece la cookie con el token
            Cookie apiCookie = new Cookie("API_KEY", token);
            apiCookie.setPath("/");
            apiCookie.setMaxAge(7 * 24 * 60 * 60); // 1 semana de duración
            response.addCookie(apiCookie);

            // Redirige a MAIN PAGE
            return "redirect:/mainPage";
        } else {
            redirectAttributes.addFlashAttribute("error", "Token incorrecto");
            return "redirect:/login";
        }
    }
}