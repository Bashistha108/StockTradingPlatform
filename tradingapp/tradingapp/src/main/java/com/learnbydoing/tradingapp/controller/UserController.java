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
    @PostMapping("/add-update-user")
    public String saveUser(@ModelAttribute("userForm") UserForm userForm, Model model) {
        System.out.println("-----------");
        System.out.println("-----------");
        System.out.println("-----------");
        System.out.println(userForm.getUser());
        System.out.println("-----------");
        System.out.println("-----------");
        System.out.println("-----------");

        try {
            // Set the user type if user is not null
            User user = userForm.getUser();
            UserType userType = userForm.getUserType();
            if (user != null && userType != null) {
                user.setUserType(userType);// Set user type only if user is not null
                userService.saveUser(user); // Save the user
                List<UserType> userTypes = userTypeService.getAllUserTypes();

                System.out.println("------------------");
                System.out.println(userTypes);
                System.out.println("------------------");

                model.addAttribute("userTypes", userTypes);
                model.addAttribute("userForm", userForm);
                return "admin/admin-users-manage";
            } else {
                model.addAttribute("errorMessage", "User or User Type cannot be null.");
                return "admin/admin-users-add-update"; // Stay on the form page with error message
            }
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Error saving user: " + e.getMessage());
        }
        model.addAttribute("userForm", userForm); // Add userForm to model again
        return "redirect:/manage-users"; // Redirect to the user list after saving
    }
    @GetMapping("/add-update-user")
    public String showFormForAddUpdate(Model model, @ModelAttribute("userForm") UserForm userForm){
        if(userForm.getUser() == null){
            userForm.setUser(new User());
        }
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





















