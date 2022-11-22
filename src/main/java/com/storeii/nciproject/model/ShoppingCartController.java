/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.storeii.nciproject.model;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
    
    @Autowired
    private OrderRepository orderRepository;
    
    @Autowired
    private OrderItemRepository orderItemRepository;
    
    @Autowired
    private SubOrderItemRepository subOrderItemRepository;
    
    @Autowired
    private OrderRepository orderRepo;
    
    
    
    @GetMapping("/cart")
    public ModelAndView showShoppingCart(int customerID) {
        // get the entity
        Customer customer = entityManager.find(Customer.class, customerID);
        
        List<CartItem> cartItems = cartItemRepo.findByCustomer(customer);   // listCartItems(customer);
        
        ModelAndView mav = new ModelAndView("shopping-cart");
        
        mav.addObject("customerId", customerID);
        mav.addObject("cartItems", cartItems);
        return mav;
    }
    
    
    @PostMapping(path="/checkout")
    public String checkout(String customerId){
        // get the entity
        Customer customer = entityManager.find(Customer.class, Integer.parseInt(customerId));
        
        System.out.println("entityManager found " + customerId);
        
        List<CartItem> cartItems = cartItemRepo.findByCustomer(customer);
        
        System.out.println("_____cartItems_____\n"+ cartItems.toString());
        
        
        // Create a new Order object, the details are added later with addOrder();
        Order order = new Order();
        orderRepository.save(order);
        
        
        
        // create a list fort the orderItems
        List<OrderItem> oList = new ArrayList<>();
        
        // Create an OrderItem for each CartItem
        for(CartItem cItem : cartItems){
            int quantity     = cItem.getQuantity();
            double unitPrice = cItem.getProduct().getPrice();
            Product product  = cItem.getProduct();
            
            
            OrderItem oItem  = new OrderItem(order, product, quantity, unitPrice);
            orderItemRepository.save(oItem);
            
            oList.add(oItem); // add the orderItem to the list
        }
        
        System.out.println("_____orderItems_____\n"+ oList.toString());
        
        
        // Run the addOrder() method
        OrderController controller = new OrderController();
        controller.addOrder(order, customer, oList, subOrderItemRepository, orderRepo);
        
        
        return "Checkout success";
    }
        
}
