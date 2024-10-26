package com.learnbydoing.tradingapp.repository;

import com.learnbydoing.tradingapp.entity.UserType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Repository for UserType entity
@Repository
public interface UserTypeRepository extends JpaRepository<UserType, Integer> {

}
