/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.storeii.nciproject.model;

/**
 *
 * @author Main
 */

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.bind.annotation.RestController;


@RestController // This means that this class is a Controller and @Rest means we don't need to include @ResponseBody
@RequestMapping(path="/webstoredb") // This means URL's start with /webstoredb (after Application path)
public class OrderItemController {
    @Autowired
    private OrderItemRepository orderItemRepository;
    
    @Autowired
    private OrderRepository orderRepository;
    
    @Autowired
    private ProductRepository productRepository;
    
    
    // Add new
    // Note: try catch wrapping here will break the server
    @PostMapping(path="/addOrderItem") // Map ONLY POST Requests
    public OrderItem addOrderItem (
        @RequestParam String orderId,
        @RequestParam String productID,
        @RequestParam String quantity,
        @RequestParam String unitPrice)
    {
      OrderItem orderItem = new OrderItem();
      
      //quantity
      orderItem.setQuantity(Integer.parseInt(quantity));
      
      //unit price
      orderItem.setUnitPrice(Double.parseDouble(unitPrice));
      
      
      // Foreign Keys
      int i = Integer.parseInt(orderId);
      orderItem.setOrder(orderRepository.getById(i));
      
      int p = Integer.parseInt(productID);
      orderItem.setProduct(productRepository.getById(p));
      
      
      
      orderItemRepository.save(orderItem);
      
      System.out.println("added new order item!");
      
      return orderItem;
    }
    
    
    
    
    // find all
    @GetMapping(path="/getOrderItems")
    public Iterable<OrderItem> getOrderItems() {
      return orderItemRepository.findAll();  // This returns a JSON or XML with the users
    }
    
   
    // for testing purposes
    @GetMapping(path="/findOrderItemsByQuantity")
    public Iterable<OrderItem> findByQuantity(@RequestParam String quantity) {
      return orderItemRepository.findByQuantity(Integer.parseInt(quantity));  // This returns a JSON or XML with the users
    }
    
    
    
    // GET ITEMS BELONGING TO A SPECIFIC ORDER
    @GetMapping(path="/getItemsFromOrder")
    //@Query("select * from OrderItems where orderID = ?")
    public List<OrderItem> getItemsFromOrder(
        @RequestParam String order
    ){
        Order realOrder = orderRepository.findById(Integer.parseInt(order)).get(); // .get() is VERY important here as it will return the actaul object and not just a reference
        return orderItemRepository.findByOrder(realOrder);
    }
    
    
    
    
    // GET ITEMS BELONGING TO A SPECIFIC ORDER BUT BETTER
    @GetMapping(path="/findOrderItemsByOrder")
    public List<OrderItem> findOrderItemsByOrder(
        @RequestParam Order order
    ){
        //Order realOrder = orderRepository.findById(Integer.parseInt(order)).get(); // .get() is VERY important here as it will return the actaul object and not just a reference
        return orderItemRepository.findByOrder(order);
    }
    
}