/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.storeii.nciproject.model;

import javax.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Main
 */

@RestController // This means that this class is a Controller and @Rest means we don't need to include @ResponseBody
@RequestMapping(path="/webstoredb") // This means URL's start with /webstoredb (after Application path)
public class CartItemController {
    @Autowired
    private CartItemRepository cartItemRepository;
    
    @Autowired
    private CustomerRepository cutomerRepository;
    
    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
    // this allows us to refer to the objects easily
    private EntityManager entityManager;
    
    // Add new
    // Note: try catch wrapping here will break the server
    @PostMapping(path="/addCartItem")           // Map ONLY POST Requests
    public String addCartItem (
        @RequestParam int customerID,
        @RequestParam int productID,
        @RequestParam int quantity
    ) {
      CartItem cartItem = new CartItem();
      
      // get the entities
      Customer customer = entityManager.find(Customer.class, customerID);
      Product product   = entityManager.find(Product.class, productID);
      
      // set the fields
      cartItem.setCustomer(customer);
      cartItem.setProduct(product);
      cartItem.setQuantity(quantity);
      
      // save the repo
      cartItemRepository.save(cartItem);
      return "saved";
    }
    
    
    
     // find all
    @GetMapping(path="/getCartItems")
    public Iterable<CartItem> getCartItems() {
      return cartItemRepository.findAll();  // This returns a JSON or XML with the users
    }
    
    
    
    @GetMapping(path="/getCartItemsByCustomer")
    public Iterable<CartItem> getCartItemsByCustomer(
        @RequestParam String customerID
    ){
        Customer customer = entityManager.find(Customer.class, Integer.parseInt(customerID));
        
        return cartItemRepository.findByCustomer(customer);
    }
}