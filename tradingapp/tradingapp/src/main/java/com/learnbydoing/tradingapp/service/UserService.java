package com.learnbydoing.tradingapp.service;

import com.learnbydoing.tradingapp.entity.User;
import com.learnbydoing.tradingapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService{

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
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

    public User createUser(User user){
        return userRepository.save(user);
    }

    public void deleteUser(Integer id){
        userRepository.deleteById(id);
    }

    public User getUserByEmail(String email){
        return userRepository.findByEmail(email);
    }

}
