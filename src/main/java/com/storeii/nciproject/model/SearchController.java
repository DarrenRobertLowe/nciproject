/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.storeii.nciproject.model;

import com.storeii.nciproject.User;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Main
 */

@RestController
public class SearchController {
    
    @Autowired
    WebsiteController webController;
    
    @Autowired
    ProductRepository productRepository;
    
    @Autowired
    SupplierRepository supplierRepository;
    
    
    @GetMapping("/search")
    public ModelAndView search (
        @RequestParam String searchTerm
    ){
        // create a Model And View to return
        ModelAndView mav = new ModelAndView();
        Queue results = new LinkedList();
        
        
        // we need to get the user context so we know how to filter the results
        String userRole = webController.getUserRole();
        
        // ANONYMOUS USERS
        if (userRole.equalsIgnoreCase("ANONYMOUS")) {
            results = searchProducts(searchTerm, null);
        } else {
            // Signed in
            User user = webController.getUser();
            
            // if it's a customer
            if (user.getCustomer() != null) {
                Location location = user.getCustomer().getLocation();
                results = searchProducts(searchTerm, location);
            }
            
            // if it's a driver
            if (user.getDriver() != null) {
                
            }
            
            // if it's a supplier
            if (user.getSupplier() != null) {
                
            }
        }
        
        
        // now we return a model and view with ther results
        // in order of similarity to the given search terms
        mav.addObject("results", results);
        mav.addObject("userRole", userRole);
        return mav;
    }
    
    
    // removes anything not a number or letter using regex
    public static String cleanString(String str) {
        str = str.replaceAll("[^a-zA-Z0-9]", "");
        return str;
    }

    
    
    public Queue searchProducts(String searchTerm, Location location) {
        // create Queues for our results
        Queue results = new LinkedList();                       // used to store direct matches. We could use ArrayList either.
        PriorityQueue extraResults = new PriorityQueue();       // used to sort other results based on compareTo()
        
        // get all the Products
        List<Product> products = productRepository.findAll();
        
        
        // this is where we need to compare our string to the
        // product name (and maybe the suppler store name)
        for(Product p : products) {
            // get rid of punctuation and make the search terms lowercase
            searchTerm = cleanString(searchTerm);
            searchTerm = searchTerm.toLowerCase();
            
            if (p.getProductName().toLowerCase().contains(searchTerm)) {
                System.out.println("p.getProductName(): " + p.getProductName());
                System.out.println("Direct Match! Adding: " + p.getProductName() );
                
                // now we'll prevent added products that contain the wrong location.
                // If we're showing all (e.g. not signed in)...
                if (location == null) {
                    System.out.println("Location is null, we're just adding everything");
                    results.add(p);
                } else {
                    // if we're filtering by location (this could be a checkmark on the search)
                    if (p.getSupplier().getLocation() == location) {
                        results.add(p);
                    }
                }
                
                // we could now iterate through the results here and
                // add them to a priorityqueue of their own.
            } else { // no direct match so use compareTo() instead...
                // all locations
                if (location == null) { 
                    System.out.println("Location is null, we're just adding everything");
                    extraResults.add(p);
                } else {
                    // filterd by location
                    if (p.getSupplier().getLocation() == location) {
                        extraResults.add(p);
                    }
                }
                
                System.out.println("adding result:" + p.getProductName());
            }
        } // end for each Product : products
        
        // merge the results queues
        for (Object r : extraResults) {
            results.add(r);
        }
        
        // return the results
        return results;
    }
}
