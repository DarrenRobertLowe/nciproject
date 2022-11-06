/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.storeii.nciproject.model;

/**
 *
 * @author Main
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.bind.annotation.RestController;


@RestController // This means that this class is a Controller and @Rest means we don't need to include @ResponseBody
@RequestMapping(path="/webstoredb") // This means URL's start with /webstoredb (after Application path)
public class OrderItemsController {
    @Autowired
    private OrderItemsRepository orderItemsRepository;
    
    @Autowired
    private OrderRepository orderRepository;
    
    @Autowired
    private ProductRepository productRepository;
    
    
    // Add new
    // Note: try catch wrapping here will break the server
    @PostMapping(path="/addOrderItems") // Map ONLY POST Requests
    public String addOrder (
        @RequestParam String order_ID,
        @RequestParam String product_ID,
        @RequestParam String quantity,
        @RequestParam String unitPrice)
    {
      OrderItems orderItems = new OrderItems();
      
      //quantity
      orderItems.setQuantity(Integer.parseInt(quantity));
      
      //unit price
      orderItems.setUnitPrice(Double.parseDouble(unitPrice));
      
      
      // Foreign Keys
      int i = Integer.parseInt(order_ID);
      orderItems.setOrder(orderRepository.getById(i));
      
      int p = Integer.parseInt(product_ID);
      orderItems.setOrder(orderRepository.getById(p));
      
      
      
      orderItemsRepository.save(orderItems);
      return "Saved";
    }
    
    // find all
    @GetMapping(path="/getOrderItems")
    public Iterable<OrderItems> getOrderItems() {
      return orderItemsRepository.findAll();  // This returns a JSON or XML with the users
    }
    
}