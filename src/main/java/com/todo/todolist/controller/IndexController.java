package com.todo.todolist.controller;

import com.todo.todolist.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
    private final UserService userService;

    public IndexController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("/findAll")
    public String findAll(Model model) {
        model.addAttribute("users",userService.getAllUsers());
        return "find-users";
    }
}
