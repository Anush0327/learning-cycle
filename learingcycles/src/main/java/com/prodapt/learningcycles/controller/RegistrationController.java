package com.prodapt.learningcycles.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.prodapt.learningcycles.entity.User;
import com.prodapt.learningcycles.service.UserService;

@Controller
@RequestMapping("/register")
public class RegistrationController {

    @Autowired
    private UserService userService;

    @GetMapping
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User()); // Assuming you have a User class
        return "register";
    }

    @PostMapping
    public String registerUser(@ModelAttribute("user") User user) {
        userService.register(user); // Implement a UserService to handle registration
        return "redirect:/login"; // Redirect to the login page after registration
    }
}

