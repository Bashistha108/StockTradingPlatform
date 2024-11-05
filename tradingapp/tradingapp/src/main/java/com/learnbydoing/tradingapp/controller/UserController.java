package com.learnbydoing.tradingapp.controller;

import com.learnbydoing.tradingapp.entity.User;
import com.learnbydoing.tradingapp.entity.UserType;
import com.learnbydoing.tradingapp.service.UserService;
import com.learnbydoing.tradingapp.service.UserTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final UserTypeService userTypeService;

    @Autowired
    public UserController(UserService userService, UserTypeService userTypeService){
        this.userService = userService;
        this.userTypeService = userTypeService;
    }

    @PostMapping("/save-user")
    public String saveUser(@ModelAttribute("userForm") UserForm userForm, Model model) {
        User user = userForm.getUser();
        int userTypeId = userForm.getUserType().getUserTypeId();
        UserType userType = userTypeService.getUserTypeById(userTypeId);
        user.setUserType(userType);
        userService.saveUser(user);
        return "redirect:/manage-users";
    }

    @GetMapping("/add-update-user-form")
    public String showFormForAddUpdate(Model model){
        UserForm userForm = new UserForm();
        model.addAttribute("userForm", userForm);
        return "admin/admin-users-add-update";
    }

    @DeleteMapping("/delete-user")
    public String deleteUser(@RequestParam("id") int id){
        userService.deleteUser(id);
        System.out.println("User deleted with id: "+id);
        return "redirect:/manage-users";
    }

   public class UserForm{
        private User user;
        private UserType userType;

       public User getUser() {
           return user;
       }

       public void setUser(User user) {
           this.user = user;
       }

       public UserType getUserType() {
           return userType;
       }

       public void setUserType(UserType userType) {
           this.userType = userType;
       }
   }

}





















