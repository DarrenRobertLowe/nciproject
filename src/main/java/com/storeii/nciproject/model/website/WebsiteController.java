/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.storeii.nciproject.model.website;

import com.storeii.nciproject.model.products.Product;
import com.storeii.nciproject.model.products.ProductRepository;
import com.storeii.nciproject.model.locations.LocationRepository;
import com.storeii.nciproject.model.locations.Location;
import com.storeii.nciproject.model.Customer.CustomerRepository;
import com.storeii.nciproject.model.Customer.Customer;
import com.storeii.nciproject.model.CartItem.CartItemRepository;
import com.storeii.nciproject.model.CartItem.CartItem;
import com.storeii.nciproject.User;
import com.storeii.nciproject.UserPrincipal;
import com.storeii.nciproject.UserService;
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
    private ProductRepository productRepository;
    
    @Autowired
    private CartItemRepository cartItemRepository;
    
    @Autowired
    private LocationRepository locationRepository;
    
    @Autowired
    private CustomerRepository customerRepository;
    
    @Autowired
    private CartService cartService;
    
    @Autowired
    private WebsiteResourcesService resources;
    
    @Autowired
    private UserService userService;
    
    
    // ROOT
    @GetMapping("/")
    public String home(Model model){
        getNavbar(model);   // get correct navbar
        return getIndex(model);
    }
    
    
    // INDEX
    @GetMapping("/index")
    public String getIndex(Model model) {
        System.out.println("*** RUNNING INDEX ****");
        // setup
        getNavbar(model);   // get correct navbar
        
        //  get the user and userRole
        String userRole = userService.getUserRole();
        model.addAttribute("userRole", userRole);
        
        
        // Get Customer and Location info for Model
        Location location = null;
        Customer customer;
        User user = userService.getUser();
        if (user != null) {
            if (user.getCustomer() != null) {
                customer = customerRepository.getById(user.getCustomer().getId());
                model.addAttribute("customer", customer);
                
                int customerId = customer.getId();
                model.addAttribute("customerId", customerId);
                
                location = locationRepository.getById(customer.getLocation().getId());
                model.addAttribute("location", location);
            }
        }
        
        
        // SHOW RANDOM PRODUCTS (per category)
        List<Product> products;
        
        // how many of each category to display on the front page
        int maxItemsPerCategory = 5;                
        int itemsPerCategory = maxItemsPerCategory;
        
        // get a list of shoes
        products = productRepository.getProductsByCategory("Shoes");                // get all shoes
        List<Product> shoes = filterProductsByLocation(products, location);         // filter by location
        Collections.shuffle(shoes);                                                 // shuffle the list
        itemsPerCategory = Integer.min(itemsPerCategory, shoes.size());             // limit the list to the number of items
        shoes = shoes.subList(0, itemsPerCategory);                                 // create a subList of the list
        model.addAttribute("shoes", shoes);                                         // add that to the model
        itemsPerCategory = maxItemsPerCategory;                                     // reset the itemsPerCategory value
        
        // get a list of coats
        products = productRepository.getProductsByCategory("Coats");                // get all coats
        List<Product> coats = filterProductsByLocation(products, location);         // filter by location
        Collections.shuffle(coats);                                                 // shuffle the list
        itemsPerCategory = Integer.min(itemsPerCategory, coats.size());             // limit the list to the number of items
        coats = coats.subList(0, itemsPerCategory);                                 // create a subList of the list
        model.addAttribute("coats", coats);
        itemsPerCategory = maxItemsPerCategory;                                     // reset the itemsPerCategory value
        
        // get a list of clothing
        products = productRepository.getProductsByCategory("Clothing");             // get all clothing
        List<Product> clothing = filterProductsByLocation(products, location);      // filter by location
        Collections.shuffle(clothing);                                              // shuffle the list
        itemsPerCategory = Integer.min(itemsPerCategory, clothing.size());          // limit the list to the number of items
        clothing = clothing.subList(0, itemsPerCategory);                           // create a subList of the list
        model.addAttribute("clothing", clothing);
        itemsPerCategory = maxItemsPerCategory;                                     // reset the itemsPerCategory value
        
        // get a list of accessories
        products = productRepository.getProductsByCategory("Accessories");          // get all accessories
        List<Product> accessories = filterProductsByLocation(products, location);   // filter by location
        Collections.shuffle(accessories);                                           // shuffle the list
        itemsPerCategory = Integer.min(itemsPerCategory, accessories.size());       // limit the list to the number of items
        accessories = accessories.subList(0, itemsPerCategory);                     // create a subList of the list
        model.addAttribute("accessories", accessories);
        
        model.addAttribute("image_directory", resources.getImageDirectory());
        
        model.addAttribute("isIndex", true); // needed for thymeleaf template to understand path
        
        return "index";
    }
    
    
    
    
    
    // get navbar
    // note both versions are needed, the 2nd version
    // allows for a ModelAndView instead of just a Model
    // and is used by FulfilmentsController.
    @GetMapping("/navbar")
    public Model getNavbar(Model model) {
        String userRole = userService.getUserRole();
        model.addAttribute("navbarRole", userRole);
        
        System.out.println("STARTING NAVBAR AS " + userRole);
        return model;//"navbar";
    }
    
    @GetMapping("/navbarAlt")
    public ModelAndView getNavbar(ModelAndView model) {
        String userRole = userService.getUserRole();
        model.addObject("navbarRole", userRole);
        
        System.out.println("STARTING NAVBAR AS " + userRole);
        return model;//"navbar";
    }
    
    
    
    
    // show products by category
    @GetMapping("/category")
    public String getProducts(Model model, @RequestParam String category) {
        String status = "invalid";
        String locationName = "none";
        
        // get the correct navbar
        getNavbar(model);
        
        // get the user so we can determine Customer
        User user = userService.getUser();
        
        // get the customer so we can determine Location
        Location location = null;
        if (user != null) {
            if (user.getCustomer() != null) {
                location = locationRepository.getById(user.getCustomer().getLocation().getId());
                locationName = location.getLocationName();
                model.addAttribute("locationName", locationName);
                status = "valid";
            }
        }
        
        List<Product> products;
        products = productRepository.getProductsByCategory(category);
        List<Product> results = filterProductsByLocation(products, location);
        model.addAttribute("products", products);

        model.addAttribute("image_directory", resources.getImageDirectory());
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
        String userRole = userService.getUserRole();
        User user = userService.getUser();
        
        
        if (user != null) {
            Customer customer = user.getCustomer();

            if (customer != null) { // Note: drivers and suppliers are treated as ANONYMOUS
                Integer customerId = customer.getId();
                model.addAttribute("customerId", customerId.toString());
                
                // check if the product already exists in the user's cart
                CartItem cartItem = cartService.checkForProductInCart(customer, product);
                if (cartItem != null) isProductInCart = true;
            }
        }
        
        
        model.addAttribute("isProductInCart", isProductInCart);
        model.addAttribute("userRole", userRole);
        model.addAttribute("product", product);
        model.addAttribute("image_directory", resources.getImageDirectory());
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
