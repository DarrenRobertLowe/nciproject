/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.storeii.nciproject.model;

import com.storeii.nciproject.User;
import com.storeii.nciproject.UserPrincipal;
import java.util.ArrayList;
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
    
    
    public String getUserRole() {
        String userRole = "ANONYMOUS";
        
        User user = getUser();
        
        if (user != null) {
            System.out.println("USER = " +user);
            userRole = user.getRole();
        } else {
            userRole = "ANONYMOUS";
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
    
    
    // show products by category
    @GetMapping("/category")
    public String getProducts(Model model, @RequestParam String category) {
        // we need to handle anonymous users differently
        String status   = "ANONYMOUS";
        String location = "none";
        List<Product> results = new ArrayList(); // list to be added to model
        Customer customer = null;
        
        // get the user role for the navbar
        String userRole = getUserRole();
        getNavbar(model);
        model.addAttribute("userType", userRole);
        
        
        // get the user so we can determine Customer
        User user = getUser();
        System.out.println("***** USER is : " + user);
        
        
        // get the customer so we can determine Location
        if (user != null) {
            customer = user.getCustomer();
            
            // get the location
            if (customer != null) {
                status = "valid";
                Location locationObj = customer.getLocation();
                
                
                if (locationObj != null) {
                    // RETURN ONLY THE ITEMS FROM THE USER'S LOCATION
                    // get the location name so we can print it, etc
                    location = locationObj.getLocationName();   
                    
                    // we want to return a list of products that belong to the location.
                    
                    // get all products
                    List<Product> products = productRepository.findAll();
                    System.out.println("********* Size of products list is " + products.size());
                    for(Product p : products) {
                        System.out.println("******** Iterating... *********");
                        
                        // get all the prodcuts
                        String categoryMatch = p.getCategory();
                        
                        if (categoryMatch.equalsIgnoreCase(category)) {
                            // results.add(p);
                            // System.out.println("******** Adding a " +category +" to the list! *********");
                            
                            // filter by location
                            if (p.getSupplier().getLocation().getId() == locationObj.getId()){
                                results.add(p);
                                System.out.println("******** Adding a " +category +" to the list! *********");
                            }
                        }
                    }
                }
            }
        }
        
        
        if (customer == null) {
            // RETURN ALL PRODUCTS FROM ALL LOCATIONS
            // get all products
            List<Product> products = productRepository.findAll();
            System.out.println("********* Size of products list is " + products.size());
            for(Product p : products) {
                System.out.println("******** Iterating... *********");

                // get all the prodcuts
                String categoryMatch = p.getCategory();

                if (categoryMatch.equalsIgnoreCase(category)) {
                    results.add(p);
                }
            }
        }
        
        
        
        
        model.addAttribute("image_directory","../assets/img/products/");
        model.addAttribute("category", category);
        model.addAttribute("results", results);
        model.addAttribute("location", location);
        model.addAttribute("status", status);
        return "category";
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
                    status = "ANONYMOUS"; // drivers and suppliers are treated as ANONYMOUS
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
