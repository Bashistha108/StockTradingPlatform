package com.learnbydoing.tradingapp.service;

import com.learnbydoing.tradingapp.entity.UserType;
import com.learnbydoing.tradingapp.repository.UserTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserTypeService {

    private final UserTypeRepository userTypeRepository;

    @Autowired
    public UserTypeService(UserTypeRepository userTypeRepository){
        this.userTypeRepository = userTypeRepository;
    }

    public List<UserType> getAllUserTypes(){
        return userTypeRepository.findAll();
    }

    public UserType getUserTypeById(Integer id){
        return userTypeRepository.findById(id).orElse(null);
    }

    public UserType createUserType(UserType userType){
        return userTypeRepository.save(userType);
    }

    public void deleteUserType(Integer id){
        userTypeRepository.deleteById(id);
    }
}
