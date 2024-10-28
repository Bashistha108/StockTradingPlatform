package com.learnbydoing.tradingapp.controller;

import com.learnbydoing.tradingapp.entity.User;
import com.learnbydoing.tradingapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class AdminController {

    private final UserService userService;

    @Autowired
    public AdminController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/admin-home")
    public String adminHome(){
        return "admin/admin-home";
    }

    @GetMapping("/manage-users")
    public String getAllUsers(Model model){
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "admin/admin-users-manage";
    }
}
