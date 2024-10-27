package com.learnbydoing.tradingapp.service;

import com.learnbydoing.tradingapp.entity.User;
import com.learnbydoing.tradingapp.entity.UserType;
import com.learnbydoing.tradingapp.repository.UserRepository;
import com.learnbydoing.tradingapp.repository.UserTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class UserService{

    private final UserRepository userRepository;
    private final UserTypeRepository userTypeRepository;

    @Autowired
    public UserService(UserRepository userRepository, UserTypeRepository userTypeRepository){
        this.userRepository = userRepository;
        this.userTypeRepository = userTypeRepository;
    }

    //default method .findAll() in JpaRepository
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    //Integer instead of int to hold null. int can't hold null, helds  0 instead which may cause confusion
    // Use Optional to avoid null pointer exception and to use isPresent... functions
    public Optional<User> getUserById(Integer id){
        return userRepository.findById(id);
    }

    public User createUser(User user) {
        user.setRegistrationDate(LocalDateTime.now()); // Set LocalDateTime directly
        UserType userType = userTypeRepository.findById(user.getUserType().getUserTypeId())
                .orElseThrow(()->new RuntimeException("UserType not found"));
        user.setUserType(userType);
        userType.getUsers().add(user);
       // userTypeRepository.save(userType); //dont need as we use CascadeType.all
        return userRepository.save(user);
    }

    public void deleteUser(Integer id){
        userRepository.deleteById(id);
        System.out.println("User with id: "+id+" deleted");
    }

    public void deleteAllUsers(){
        userRepository.deleteAll();
    }

    public User getUserByEmail(String email){
        return userRepository.findByEmail(email);
    }

}
