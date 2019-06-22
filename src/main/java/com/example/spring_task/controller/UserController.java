package com.example.spring_task.controller;

import com.example.spring_task.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    @GetMapping("/register")
    public String addUserr(Model model){
        model.addAttribute("user", new User());
        return "adduser";
    }

}
