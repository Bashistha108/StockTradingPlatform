package com.learnbydoing.tradingapp.controller;

import com.learnbydoing.tradingapp.entity.UserType;
import com.learnbydoing.tradingapp.service.UserTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user-types")
public class UserTypeController {

    private final UserTypeService userTypeService;

    @Autowired
    public UserTypeController(UserTypeService userTypeService){
        this.userTypeService = userTypeService;
    }

    @GetMapping
    public List<UserType> getAllUserTypes(){
        return userTypeService.getAllUserTypes();
    }
/*
    @PostMapping("/create")
    public UserType createUserType(@RequestBody UserType userType){
        return userTypeService.createUserType(userType);
    }
*/
    @DeleteMapping("/delete/{id}")
    public void deleteUserType(@PathVariable int id){
        userTypeService.deleteUserType(id);
    }
}
