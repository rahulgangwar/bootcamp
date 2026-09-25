package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.SimpleDateFormat;
import java.util.Date;

@RequestMapping("/page")
@Controller
public class PageController {

    @GetMapping("/home")
    public String home(Model model) {
        model.addAttribute("date", new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
        model.addAttribute("message", "Welcome");
        return "home";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }
}
