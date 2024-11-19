
package com.learnbydoing.tradingapp.config;

import com.learnbydoing.tradingapp.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration      // Configiruation is used to define beans. Spring search for Beans in Configuration
@EnableWebSecurity  // Enables web security support
public class SecurityConfig {

    private final AuthenticationSuccessHandler customAuthenticationSuccessHandler;  // Used to handle successful authentication events
    private final CustomUserDetailsService customUserDetailsService;
    @Autowired
    public SecurityConfig(AuthenticationSuccessHandler customAuthenticationSuccessHandler, CustomUserDetailsService customUserDetailsService){
        this.customAuthenticationSuccessHandler = customAuthenticationSuccessHandler;
        this.customUserDetailsService = customUserDetailsService;
    }

    @Bean   //Method will return a bean that should be managed by Spring context
    //Security FilterChain configures the securtiy filter chain using HttpSecurity Object for Http requests
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
            http
                .authorizeHttpRequests(configurer -> configurer
                        .requestMatchers("/admin/**").permitAll()
                        .requestMatchers("/search/**").permitAll()

                        //to allow the css files to render properly
                        .requestMatchers("/css/**", "/js/**", "/images/**", "/static/**").permitAll()

                        .requestMatchers("/users/delete-user/**").permitAll()
                        .requestMatchers("/manage-users", "/stocks/**", "/stocks/search/**", "/stocks/history/**", "/stocks/update/**").permitAll()
                        .requestMatchers("/", "/users/*", "/login-page", "/access-denied", "/users/**").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login-page")
                        .loginProcessingUrl("/authenticateTheUser")
                        .successHandler(customAuthenticationSuccessHandler)  // WHen authentication successfull go the customAuthenticationSUccessHandler
                        .permitAll()
                )
                    .logout(logout -> logout
                            .logoutUrl("/logout") // Define logout URL (default is "/logout")
                            .logoutSuccessUrl("/login-page?logout") // Redirect after successful logout
                            .invalidateHttpSession(true) // Invalidate session
                            .deleteCookies("JSESSIONID") // Remove session cookie
                            .permitAll() // Allow all users to log out
                    )
                .exceptionHandling(configurer -> configurer .accessDeniedPage("/access-denied"))
                    .csrf(csrf->csrf.disable());

        return http.build();
    }

    /**
     * Autowired is automatically called by Spring Security during initialization process
     * auth.userDetailsService(CustomUserDetailsService) registers CustomUserDetailsService with Spring Security
     * Ensures Spring uses custom logic to load user details
    **/

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(customUserDetailsService);
    }


    //Creating a bean so that can be used just by dependency injection
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}

