/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.storeii.nciproject.model;

import java.util.List;
import javax.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
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
    private EntityManager entityManager;    // this allows us to refer to the entities mroe easily
    
    @Autowired
    private CartItemRepository cartItemRepo;
    
    
    @GetMapping("/cart")
    public ModelAndView showShoppingCart(int customerID) {
        // get the entity
        Customer customer = entityManager.find(Customer.class, customerID);
        
        List<CartItem> cartItems = cartItemRepo.findByCustomer(customer);   // listCartItems(customer);
        
        ModelAndView mav = new ModelAndView("shopping-cart");
        
        mav.addObject("cartItems", cartItems);
        return mav;
    }
}