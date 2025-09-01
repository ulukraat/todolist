package com.todo.todo.controller;

import com.todo.todo.model.User;
import com.todo.todo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String getLoginForm(Model model) {
        model.addAttribute("user", new User());
        return "user-login";
    }

    @PostMapping("/login")
    public String postLogin(@ModelAttribute("user")  User user, Model model) {
        if(userService.isValid(user)) {
            return "redirect:/tasks/";
        } else {
            model.addAttribute("message", "Invalid username or password.");
            return "user-login";
        }
    }
}
