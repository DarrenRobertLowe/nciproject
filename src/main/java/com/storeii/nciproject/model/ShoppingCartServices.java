/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.storeii.nciproject.model;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Main
 */

@Service
public class ShoppingCartServices {
    // these methods will be used by the Customer.
    
    @Autowired
    private CartItemRepository cartItemRepo;
    
    public List<CartItem> listCartItems(Customer customer) {
        return cartItemRepo.findByCustomer(customer);
    }
}
