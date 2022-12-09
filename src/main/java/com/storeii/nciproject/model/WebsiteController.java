/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.storeii.nciproject.model;

import com.storeii.nciproject.User;
import com.storeii.nciproject.UserPrincipal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
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
    
    @Autowired
    CartItemRepository cartItemRepository;
    
    
    
    @GetMapping("/accessories")
    public String getAccessories(Model model) {
        getNavbar(model);
        return "accessories";
    }
    
    
    
    public String getUserRole() {
        String userRole = "ANONYMOUS";
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Object principal = auth.getPrincipal();
        
        if (principal == "anonymousUser") {
            userRole = "ANONYMOUS";
        } else {
            UserPrincipal userPrincipal = (UserPrincipal)principal; // make sure you're logged in or you'll get an error!
            
            if (principal instanceof UserDetails) {
                User user = userPrincipal.getUser();
                System.out.println("USER = " +user);
                userRole = user.getRole();
            } else {
                userRole = "ANONYMOUS";
            }
        }
        
        return userRole;
    }
    
    
    
    @GetMapping("/navbar")
    public Model getNavbar(Model model) {
        String userRole = getUserRole();
        model.addAttribute("userRole", userRole);
        
        System.out.println("STARTING NAVBAR AS " + userRole);
        return model;//"navbar";
    }
    
    @GetMapping("/navbarAlt")
    public ModelAndView getNavbar(ModelAndView model) {
        String userRole = getUserRole();
        model.addObject("userRole", userRole);
        
        System.out.println("STARTING NAVBAR AS " + userRole);
        return model;//"navbar";
    }
    
    
    
    @GetMapping("/index")
    public String getAll(Model model) {
        
        String userRole = getUserRole();
        
        getNavbar(model);
        model.addAttribute("userType", userRole);
        return "index";
    }
    
    
    
    
    
    @GetMapping(path = "/productpage")
    public String productListing(
        Model model,
        @RequestParam String identifier
    ){
        // GET THE PRODUCT
        Product product = productRepository.findByIdentifier(identifier);
        boolean isProductInCart = false;
        
        //CHECK IF USER IS LOGGED IN
        String status = "ANONYMOUS";
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        
        if (principal == "anonymousUser") {
            System.out.println("USER IS NOT LOGGED IN!");
            status = "ANONYMOUS";
        } else {
            UserPrincipal userPrincipal = (UserPrincipal)principal; // make sure you're logged in or you'll get an error!
            
            
            if (principal instanceof UserDetails) {
                User user = userPrincipal.getUser();
                System.out.println("USER = " +user);
                
                Customer customer = user.getCustomer();
                
                if (customer == null) {
                    status = "ANONYMOUS";
                } else {
                   System.out.println("CUSTOMER = " + customer);
                   System.out.println("customer.id = " + customer.getId());
                   
                   Integer customerId = customer.getId();
                   status = "AUTHORIZED";
                   model.addAttribute("customerId", customerId.toString());
                   
                   // GET THE CUSTOMER'S CART ITEMS
                   List<CartItem> cartItemList = cartItemRepository.findByCustomer(customer);
                   
                   for(CartItem item: cartItemList) {
                       if (item.getProduct() == product) {
                           System.out.println("***** PRODUCT WAS ALREADY IN CUSTOMER CART *****");
                           isProductInCart = true;
                       }
                   }
                   
                }
            } else {
                status = "ANONYMOUS";
            }
        }
        
        
        model.addAttribute("isProductInCart", isProductInCart);
        model.addAttribute("status", status);
        model.addAttribute("product", product);
        model.addAttribute("image_directory","../assets/img/products/");
        getNavbar(model);
        
        return "productpage"; //return model
    }
    
    
    @GetMapping(path = "/orderplaced")
    public String orderplaced(Model model) {
        return "orderplaced";
    }
    
    
    @GetMapping(path = "/loggedout")
    public String loggedout(Model model) {
        return "loggedout";
    }
}
