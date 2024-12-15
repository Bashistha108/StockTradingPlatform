package com.learnbydoing.tradingapp.repository;

import com.learnbydoing.tradingapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//Repository for User users JPARepository for all CRUD functionalities and our own custom query
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    //this method declared because JpaRepository dont have it by default. JpaRepository will use sql queries for
    //findby... in this case; SELECT * FROM user WHERE email = ...
    User findByUserId(int userId);
    User findByEmail(String email);

}
