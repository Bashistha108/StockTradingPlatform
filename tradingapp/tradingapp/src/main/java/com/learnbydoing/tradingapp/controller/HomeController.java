package com.learnbydoing.tradingapp.controller;

import com.learnbydoing.tradingapp.entity.User;
import com.learnbydoing.tradingapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;

@Controller
public class HomeController {

    private final UserService userService;

    @Autowired
    public HomeController(UserService userService){
        this.userService = userService;
    }


    @GetMapping("/")
    public String home(Principal principal, Model model){
        String email = principal.getName();
        User user = userService.getUserByEmail(email);
        if(email == null){
            System.out.println("User not found ..........."+email);
        }
        model.addAttribute("user", user);
        return "home";
    }


}
