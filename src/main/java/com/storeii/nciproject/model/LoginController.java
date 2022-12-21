/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.storeii.nciproject.model;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import com.storeii.nciproject.User;
import com.storeii.nciproject.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
/**
 *
 * @author Main
 */


@Controller
public class LoginController {
    @Autowired
    WebsiteController websiteController;
    
    
    @GetMapping(path = "/login")
    public String loginPage() {
        return "login";
    }
    
    
    @PostMapping(path = "/processLogin")
    public void processLogin() {
        //return "home";
        System.out.println("**** /login/process invoked ****");
    }
    /*
    @GetMapping(path = "/loginSuccess")
    public String loginSuccess(){
        // get the user role for the navbar
        String userRole = websiteController.getUserRole();
        String url = "/loginFailed";
        
        if ( userRole.equalsIgnoreCase("CUSTOMER") ) {
            url =  "/index";
        }
        
        if ( userRole.equalsIgnoreCase("SUPPLIER") ) {
            url =  "/fulfilments";
        }
        
        if ( userRole.equalsIgnoreCase("DRIVER") ) {
            url = "/deliveries";
        }
        // get the user so we can determine Customer
        //User user = getUser();
        
        return url;
    }
    */
}
