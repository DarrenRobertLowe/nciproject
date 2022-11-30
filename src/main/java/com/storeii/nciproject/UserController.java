/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.storeii.nciproject;

import com.storeii.nciproject.UserRepository;
import com.storeii.nciproject.model.Customer;
import com.storeii.nciproject.model.CustomerController;
import javax.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Main
 */

@EnableWebSecurity
@Controller
//@RequestMapping(path="/webstoredb") // This means URL's start with /webstoredb (after Application path)
public class UserController {
    // AUTOWIRES //
    
    @Autowired
    UserRepository userRepository;
    
    @Autowired
    private EntityManager entityManager;
    
    @Autowired
    PasswordEncoder passwordEncoder;    // this loads bCryptEncoder via the CustomSecurityConfig class
    
    
    @RequestMapping("/perform_login")
    public String loginPage() { 
        System.out.println("*********** Going to LOGIN page ***********");
        return "/login";
    }
    
    @RequestMapping("/perform_logout")
    public String logout() { 
        return "/logout"; 
    }
    
    @GetMapping("/perform_register")
    public String register() { 
        System.out.println("*********** Going to REGISTER page ***********");
        return "/register"; 
    }
    
    @RequestMapping("/index")
    public String homePage() { 
        return "/index"; 
    }
    
    
    
    // Login form
    @RequestMapping("/login.html")
    public String login() {
      return "login.html";
    }

    // Login form with error
    @RequestMapping("/login-error.html")
    public String loginError(Model model) {
      model.addAttribute("loginError", true);
      return "login.html";
    }
    
    
    
    // find all customers
    @GetMapping(path="/getUsers")
    public Iterable<User> getUsers() {
        return userRepository.findAll();  // This returns a JSON or XML with the users
    }
    
    
    
    
    
    // CUSTOMER
    // Adding a new Customer
    // Note: try catch wrapping here will break the server
    @GetMapping(path="/addUserCustomer")
    public String addUser (
        @RequestParam String firstName,
        @RequestParam String surname,
        @RequestParam String userName,
        @RequestParam String userPass,
        @RequestParam String role,
        //@RequestParam String customerId,
        //@RequestParam String driverId,
        //@RequestParam String supplierId
    ){
        User n = new User();
        n.setFirstName(firstName);
        n.setSurname(surname);
        n.setUserName(userName);
        n.setRole("USER");
        
        
        /********
        // at this point we need to create a new Customer, Driver 
        // or Supplier depending perhaps on the ROLE.
        // After that we can assign customerId, driverId, supplierId.
        ********/
        if (role.equalsIgnoreCase("USER")) {   // This could actually point to a table entry
            CustomerController.addCustomer();
            Customer customer = entityManager.find(Customer.class, customerId);
        }
        
        // get the entities
        if (!customerId.equals("")) {
            Customer customer = entityManager.find(Customer.class, customerId);
        }
        if (!driverId.equals("")) {
            Customer driver = entityManager.find(Customer.class, driverId);
        }
        if (!supplierId.equals("")) {
            Customer suplier = entityManager.find(Customer.class, supplierId);
        }
        
        
        // encrypt password
        String encryptedPass = passwordEncoder.encode(userPass);
        n.setUserPass(encryptedPass);
        
        
        
        
        // save the new user
        userRepository.save(n);
        return "registration-success";
    }
    
}
