/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.storeii.nciproject.model;

import com.storeii.nciproject.User;
import com.storeii.nciproject.UserPrincipal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Main
 */
@Controller
public class WebsiteController {

    @Autowired
    ProductRepository productRepository;
    
    
    @GetMapping("/index")
    public String getAll(Model model) {
        //List<Location> locations = locationRepo.findAll();
       // model.addAttribute("locations", locations);
        
        return "index";
    }
    
    @GetMapping(path = "/productpage")
    public ModelAndView productListing(
        ModelAndView model,
        @RequestParam String identifier
    ){
        Product product = productRepository.findByIdentifier(identifier);
        
        // now we have the supplier object we can check if the User id corresponds.
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserPrincipal userPrincipal = (UserPrincipal)principal; // make sure you're logged in or you'll get an error!
        
        boolean valid = false;
        
        //String username = "None";
        if (principal instanceof UserDetails) {
            User user = userPrincipal.getUser();
            System.out.println("USER = " +user);
            
            Customer customer = user.getCustomer();
            System.out.println("CUSTOMER = " + customer);
            System.out.println("customer.id = " + customer.getId());
            Integer customerId = customer.getId();
            
            if (customer == null) {
                valid = false;
            } else {
               model.addObject("customerId", customerId.toString());
            }
        } else {
            System.out.println("***** ACCESS DENIED *****");
            valid = false;
        }
        
        
        model.addObject("loginIsValid", valid);
        model.addObject("product", product);
        model.addObject("image_directory","../assets/img/products/");
        return model;//"productpage";
    }
    
    
    
}
