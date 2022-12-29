/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.storeii.nciproject.model;

import com.storeii.nciproject.User;
import com.storeii.nciproject.UserPrincipal;
import java.util.ArrayList;
import java.util.Collections;
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
    
    @Autowired
    LocationRepository locationRepository;
    
    @Autowired
    CustomerRepository customerRepository;
    
    
    @GetMapping("/")
    public String home(Model model){
        getNavbar(model);   // get correct navbar
        
        return getIndex(model);
    }
    
    @GetMapping("/index")
    public String getIndex(Model model) {
        System.out.println("*** RUNNING INDEX ****");
        // setup
        getNavbar(model);   // get correct navbar
        String userRole = getUserRole();
        User user = getUser();
        Location location = null;
        int itemsPerCategory = 5;   // how many of each category to display on the front page
        
        if (user != null) {
            if (user.getCustomer() != null) {
                Customer customer = customerRepository.getById(user.getCustomer().getId());
                location = locationRepository.getById(user.getCustomer().getLocation().getId());
                model.addAttribute("customer", customer);
                model.addAttribute("location", location);
            } else {
            }
        }
        model.addAttribute("userRole", userRole);
        
        
        // Show a randomized list of items per category
        List<Product> products;
        
        if (location != null) {
            System.out.println("****** LOCATION is " + location.getLocationName());
        }
        // get a list of shoes
        products = productRepository.getProductsByCategory("Shoes");
        List<Product> shoes = filterProductsByLocation(products, location);         // get the products
        Collections.shuffle(shoes);                                                 // suffle the list
        itemsPerCategory = Integer.min(itemsPerCategory, shoes.size());             // limit the list to the number of items
        shoes = shoes.subList(0, itemsPerCategory);                                 // create a subList of the list
        model.addAttribute("shoes", shoes);                                         // add that to the model
        
        
        // get a list of coats
        products = productRepository.getProductsByCategory("Coats");
        List<Product> coats = filterProductsByLocation(products, location);
        Collections.shuffle(coats);                                                 // suffle the list
        itemsPerCategory = Integer.min(itemsPerCategory, coats.size());             // limit the list to the number of items
        coats = coats.subList(0, itemsPerCategory);                                 // create a subList of the list
        model.addAttribute("coats", coats);
        
        // get a list of clothing
        products = productRepository.getProductsByCategory("Clothing");
        List<Product> clothing = filterProductsByLocation(products, location);
        Collections.shuffle(clothing);                                              // suffle the list
        itemsPerCategory = Integer.min(itemsPerCategory, clothing.size());          // limit the list to the number of items
        clothing = clothing.subList(0, itemsPerCategory);                           // create a subList of the list
        model.addAttribute("clothing", clothing);
        
        // get a list of accessories
        products = productRepository.getProductsByCategory("Accessories");
        List<Product> accessories = filterProductsByLocation(products, location);
        Collections.shuffle(accessories);                                           // suffle the list
        itemsPerCategory = Integer.min(itemsPerCategory, accessories.size());       // limit the list to the number of items
        accessories = accessories.subList(0, itemsPerCategory);                     // create a subList of the list
        model.addAttribute("accessories", accessories);

        model.addAttribute("image_directory","../assets/img/products/");
        
        
        return "index";
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
    
    
    // GET USER ROLE
    // returns the role value from the given User
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
    
    
    // get navbar
    // note both versions are needed, the 2nd version
    // allows for a ModelAndView instead of just a Model
    // and is used by FulfilmentsController.
    @GetMapping("/navbar")
    public Model getNavbar(Model model) {
        String userRole = getUserRole();
        model.addAttribute("navbarRole", userRole);
        
        System.out.println("STARTING NAVBAR AS " + userRole);
        return model;//"navbar";
    }
    
    @GetMapping("/navbarAlt")
    public ModelAndView getNavbar(ModelAndView model) {
        String userRole = getUserRole();
        model.addObject("navbarRole", userRole);
        
        System.out.println("STARTING NAVBAR AS " + userRole);
        return model;//"navbar";
    }
    
    
    
    
    // show products by category
    @GetMapping("/category")
    public String getProducts(Model model, @RequestParam String category) {
        // we need to handle anonymous users differently
        String status = "invalid";
        String locationName = "none";
        //List<Product> results = new ArrayList(); // list to be added to model
        Customer customer = null;
        
        // get the user role for the navbar
        //String userRole = getUserRole();
        getNavbar(model);
        //model.addAttribute("userType", userRole);
        
        
        // get the user so we can determine Customer
        User user = getUser();
        System.out.println("***** USER is : " + user);
        
        
        // get the customer so we can determine Location
        Location location = null;
        if (user != null) {
            if (user.getCustomer() != null) {
                location = locationRepository.getById(user.getCustomer().getLocation().getId());
                locationName = location.getLocationName();
                model.addAttribute("locationName", locationName);
                status = "valid";
            } else {
                //userRole = "ANONYMOUS";
            }
        }
        //model.addAttribute("userRole", userRole);
        
        
        List<Product> products;
        products = productRepository.getProductsByCategory(category);
        List<Product> results = filterProductsByLocation(products, location);
        model.addAttribute("products", products);

        
        model.addAttribute("image_directory","../assets/img/products/");
        model.addAttribute("category", category);
        model.addAttribute("results", results);
        model.addAttribute("locationName", locationName);
        model.addAttribute("status", status);
        return "category";
    }
    
    
    
    
    
    // FILTER PRODUCTS BY LOCATION
    public List filterProductsByLocation(List<Product> products, Location location) {
        
        List<Product> returnList = new ArrayList<>();
        
        for(Product product : products) {
            if ( location != null ) {
                if (product.getSupplier().getLocation().getId() == location.getId()) {
                    // add the product
                    returnList.add(product);
                }
            } else {
                // not filtered by location so add the product
                returnList.add(product);
            }
        }
        
        return returnList;
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
    
    /*
    @GetMapping("/accessories")
    public String getAccessories(Model model) {
        getNavbar(model);
        return "accessories";
    }*/
    
    
    @GetMapping(path = "/orderplaced")
    public String orderplaced(Model model) {
        return "orderplaced";
    }
    
    
    @GetMapping(path = "/loggedout")
    public String loggedout(Model model) {
        return "loggedout";
    }
    
}
