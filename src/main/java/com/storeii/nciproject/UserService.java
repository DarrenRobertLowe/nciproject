/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.storeii.nciproject;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

/**
 *
 * @author Darren Robert Lowe
 */
@Service
public class UserService {
    public User getUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Object principal = auth.getPrincipal();
        User user = null;
        
        if (principal != "anonymousUser") {
            UserPrincipal userPrincipal = (UserPrincipal)principal; // make sure you're logged in or you'll get an error!
            
            if (principal instanceof UserDetails) {
                user = userPrincipal.getUser();
            }
        }
        
        return user;
    }
    
    
    // GET USER ROLE
    // returns the role value from the given User
    public String getUserRole() {
        String userRole = "ANONYMOUS";
        
        User user = getUser();
            
        if (user != null) {
            userRole = user.getRole();
        }
        
        return userRole;
    }
}
