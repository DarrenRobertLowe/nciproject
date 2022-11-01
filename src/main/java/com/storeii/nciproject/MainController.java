/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.storeii.nciproject;

/**
 *
 * @author Main
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller // This means that this class is a Controller
@RequestMapping(path="/webstoredb") // This means URL's start with /demo (after Application path)
public class MainController {
  @Autowired // This means to get the bean called userRepository
         // Which is auto-generated by Spring, we will use it to handle the data
  private CustomerRepository customerRepository;
  
  
  // Adding a new Customer
  @PostMapping(path="/addNewCustomer") // Map ONLY POST Requests
  public @ResponseBody String addNewCustomer (@RequestParam String firstName
      , @RequestParam String surname
      , @RequestParam String userName
      , @RequestParam String userPass){
    // @ResponseBody means the returned String is the response, not a view name
    // @RequestParam means it is a parameter from the GET or POST request
    
    Customer n = new Customer();
    n.setFirstName(firstName);
    n.setSurname(surname);
    n.setUserName(userName);
    n.setUserPass(userPass);
    
    customerRepository.save(n);
    return "Saved";
  }
  
  
  // find all customers
  @GetMapping(path="/getAllCustomers")
  public @ResponseBody Iterable<Customer> getAllCustomers() {
    // This returns a JSON or XML with the users
    return customerRepository.findAll();
  }
}