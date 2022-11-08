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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController // This means that this class is a Controller and @Rest means we don't need to include @ResponseBody
@RequestMapping(path="/webstoredb") // This means URL's start with /webstoredb (after Application path)
public class SubOrderItemController {
    @Autowired
    private SubOrderItemRepository subOrderItemRepository;
    
    @Autowired
    private SubOrderRepository subOrderRepository;
    
    @Autowired
    private ProductRepository productRepository;
    
    
    
    // Add new
    // Note: try catch wrapping here will break the server
    @PostMapping(path="/addSubOrderItem") // Map ONLY POST Requests
    public String addSubOrderItem (
        @RequestParam String subOrderId,
        @RequestParam String productId,
        @RequestParam String quantity
    )
    {
      SubOrderItem subOrderItem = new SubOrderItem();
      
      //quantity
      subOrderItem.setQuantity(Integer.parseInt(quantity));
      
      // Foreign Keys
      int i = Integer.parseInt(subOrderId);
      subOrderItem.setSubOrder(subOrderRepository.getById(i));
      
      int p = Integer.parseInt(productId);
      subOrderItem.setProduct(productRepository.getById(p));
      
      
      subOrderItemRepository.save(subOrderItem);
      return "Saved";
    }
    
    
    // find all
    @GetMapping(path="/getSubOrderItems")
    public Iterable<SubOrderItem> getSubOrderItems() {
      return subOrderItemRepository.findAll();  // This returns a JSON or XML with the users
    }
    
    
    
    // GET ITEMS BELONGING TO A SPECIFIC SUBORDER
    @GetMapping(path="/getItemsFromSubOrder")
    //@Query("select * from SubOrderItems where subOrderID = ?")
    public List<SubOrderItem> getItemsFromSubOrder (
        @RequestParam String subOrder
    ){
        SubOrder realOrder = subOrderRepository.findById(Integer.parseInt(subOrder)).get(); // .get() is VERY important here as it will return the actaul object and not just a reference
        return subOrderItemRepository.findBySubOrder(realOrder);
    }
    
}