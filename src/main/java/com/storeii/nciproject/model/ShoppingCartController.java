/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.storeii.nciproject.model;

import java.util.List;
import javax.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Main
 */


@RestController
public class ShoppingCartController {
    
    @Autowired
    private ShoppingCartServices cartServices;
    
    @Autowired
    // this allows us to refer to the objects easily
    private EntityManager entityManager;
    
    
    @Autowired
    private CartItemRepository cartItemRepo;
    
    
    /*
    @GetMapping("/cart")
    public String showShoppingCart(Model model, int customerID){   // NOTE!!! THIS SHOULD BE AN AUTHENTICATED OBJECT!
        
        // Integer customerInt = (customerID);
        
        
        // get the entities
        Customer customer = entityManager.find(Customer.class, customerID);
        
        List<CartItem> cartItems = cartItemRepo.findByCustomer(customer);   // listCartItems(customer);
        
        model.addAttribute("cartItems", cartItems);
        //model.addAttribute("pageTitle", "Shopping Cart");
        
        return "shopping_cart"; // this is the name of the html page for the shopping cart
    }
    */
    
    @GetMapping("/cart")
    public ModelAndView showShoppingCart(int customerID) {
        
        // get the entities
        Customer customer = entityManager.find(Customer.class, customerID);
        
        List<CartItem> cartItems = cartItemRepo.findByCustomer(customer);   // listCartItems(customer);
        
        ModelAndView mav = new ModelAndView("shopping-cart");
        
        mav.addObject("cartItems", cartItemRepo.findAll());
        return mav;
    }
}
