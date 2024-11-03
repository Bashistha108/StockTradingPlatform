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

    /*
    @GetMapping
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Integer id){
        Optional<User> user = userService.getUserById(id);
        // Convert User to ResponseEntity if Present
        return user.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

     @DeleteMapping("/delete/{id}")
    public void deleteUser(@PathVariable int id){
        userService.deleteUser(id);

    }

    @DeleteMapping("/deleteAllUsers")
    public void deleteAllUsers(){
        userService.deleteAllUsers();
    }

    @PutMapping("/update/{id}")
    public void updateUser(@PathVariable int id, @RequestBody User user){
        userService.updateUser(id, user);
    }


*/
    @PostMapping("/save-user")
    public String saveUser(@ModelAttribute("userForm") UserForm userForm, Model model) {
        User user = userForm.getUser();
        UserType userType = userForm.getUserType();
        user.setUserType(userType);
        userService.saveUser(user);
        Integer id = user.getUserId();
        userTypeService.createUserType(id, userType);

        return "redirect:/manage-users";
    }
    @GetMapping("/add-update-user-form")
    public String showFormForAddUpdate(Model model){
        UserForm userForm = new UserForm();
        model.addAttribute("userForm", userForm);
        return "admin/admin-users-add-update";
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





















