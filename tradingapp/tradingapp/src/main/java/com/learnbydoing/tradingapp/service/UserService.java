package com.learnbydoing.tradingapp.service;

import com.learnbydoing.tradingapp.entity.User;
import com.learnbydoing.tradingapp.entity.UserType;
import com.learnbydoing.tradingapp.repository.UserRepository;
import com.learnbydoing.tradingapp.repository.UserTypeRepository;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class UserService{

    private final UserRepository userRepository;
    private final UserTypeRepository userTypeRepository;
    private final PasswordEncoder passwordEncoder; //For Bcrypting

    @Autowired
    public UserService(UserRepository userRepository, UserTypeRepository userTypeRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.userTypeRepository = userTypeRepository;
        this.passwordEncoder = passwordEncoder;
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

        //Hashing the password before saving it
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);

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

    public User updateUser(Integer userId, User user){
        User currentUser = userRepository.getById(userId);
        currentUser.setEmail(user.getEmail());
        currentUser.setActive(user.isActive());
        currentUser.setFirstName(user.getFirstName());
        currentUser.setLastName(user.getLastName());
        currentUser.setProfilePhoto(user.getProfilePhoto());
        currentUser.setPassword(user.getPassword());
        return userRepository.save(user);
    }

}
