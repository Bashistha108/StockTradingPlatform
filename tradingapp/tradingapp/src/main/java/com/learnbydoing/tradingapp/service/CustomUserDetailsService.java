package com.learnbydoing.tradingapp.service;

import com.learnbydoing.tradingapp.config.CustomUserDetails;
import com.learnbydoing.tradingapp.entity.User;
import com.learnbydoing.tradingapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Collections;

/**
 * This class implements UserDetailsService which provides methods for fetching user details based on username(email in our casse)
 * Responsible for fetching user specific data from datasource like database and returning instance of UserDetails in our case CustomUserDetails
 * */

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
  //  private final PasswordEncoder passwordEncoder;  //Just Injecting the PasswordEncoder defined in SecurityCOnfig will work,
    // as the instance of bean will be created in SecurityConfig

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository){
        this.userRepository = userRepository;
       // this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);
        if(user == null){
            throw new UsernameNotFoundException("User not found");
        }
        return new CustomUserDetails(user.getEmail(), user.getPassword(), Collections.singletonList(() -> "ROLE_"+ user.getRole()));
    }

    // WE created a new funtion to avoid circular bean reference
    public String encodePassword(String rawPassword){
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(rawPassword);
    }

}
