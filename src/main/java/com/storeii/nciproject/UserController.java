/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.storeii.nciproject;

import com.storeii.nciproject.UserRepository;
import com.storeii.nciproject.model.Address;
import com.storeii.nciproject.model.AddressController;
import com.storeii.nciproject.model.Customer;
import com.storeii.nciproject.model.CustomerController;
import com.storeii.nciproject.model.CustomerRepository;
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
    private CustomerController customerController;
    
    @Autowired
    private CustomerRepository customerRepository;
    
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
    @GetMapping(path="/addUserCustomer")
    public String addUser (
        @RequestParam String firstName,
        @RequestParam String surname,
        @RequestParam String userName,
        @RequestParam String userPass,
        //@RequestParam String role,
        @RequestParam String location,
        @RequestParam String addressLine1,
        @RequestParam String addressLine2,
        @RequestParam String city,
        @RequestParam String district,
        @RequestParam String postcode,
        @RequestParam String country
    ){
        User user = new User();
        user.setFirstName(firstName);
        user.setSurname(surname);
        user.setUserName(userName);
        user.setRole("USER");
        
        // encrypt password
        String encryptedPass = passwordEncoder.encode(userPass);
        user.setUserPass(encryptedPass);
        
        
        /********
        * At this point we need to create a new Customer object.
        * Before we can do that we need to create the address.
        * After that we can assign customerId to the User.
        ********/
        
        // CREATE ADDRESS
        AddressController ac = new AddressController();
        Address address = ac.addAddress(addressLine1, addressLine2, city, district, postcode, country);
        String addressId = ((Integer)address.getId()).toString(); // get the addressId as a String for creating the Customer
        
        // CREATE CUSTOMER
        CustomerController cc = new CustomerController();
        Customer newCustomer = cc.addCustomer(firstName, surname, userName, userPass, addressId, location);
        
        // SET THE CUSTOMER
        user.setCustomer(newCustomer);
        
        // SAVE THE USER
        userRepository.save(user);
        return "registration-success";
    }
    
}
