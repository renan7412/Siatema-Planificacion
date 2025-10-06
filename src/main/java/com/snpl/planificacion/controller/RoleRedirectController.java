package com.snpl.planificacion.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RoleRedirectController {

    @GetMapping("/redirectByRole")
    public String redirectByRole(Authentication authentication) {
        if (authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"))) {
            return "redirect:/admin/dashboard";
        } else if (authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_USER"))) {
            return "redirect:/user/home";
        }
        return "redirect:/auth/login?error";
    }
}
