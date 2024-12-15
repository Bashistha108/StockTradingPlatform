

package com.learnbydoing.tradingapp.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * This component defines actions that should be taken when a user successfully authenticates to the application
 * */

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    public static final String ROLE_ADMIN = "ROLE_Admin";
    public static final String ROLE_TRADER = "ROLE_Trader";

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
            //Custom Logic after successfull authentication like redirecting to page based on role
        if(authentication.getAuthorities().stream().anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(ROLE_ADMIN))){
            response.sendRedirect("/admin-home");
        }else if(authentication.getAuthorities().stream().anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(ROLE_TRADER))){
            response.sendRedirect("/trader-home");
        }
        else{
            response.sendRedirect("/");
        }
    }
}
