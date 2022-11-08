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
public class SubOrderController {
    @Autowired
    private SubOrderRepository subOrderRepository;
    
    @Autowired
    private OrderRepository orderRepository;
    
    @Autowired
    private SupplierRepository supplierRepository;
    
    
    
    // Add new
    // Note: try catch wrapping here will break the server
    @PostMapping(path="/addSubOrder") // Map ONLY POST Requests
    public String addSubOrder (
        @RequestParam String orderStatus,      // this should be set to 1 for new orders
        @RequestParam String order_ID,
        @RequestParam String supplier_ID
    )
    {
      SubOrder suborder = new SubOrder();
      
      
      // set fields
      int status = Integer.parseInt(orderStatus);
      int order = Integer.parseInt(order_ID);
      int supplier = Integer.parseInt(supplier_ID);
      
      suborder.setOrderStatus(status);
      suborder.setOrder(orderRepository.getById(order));
      suborder.setSupplier(supplierRepository.getById(supplier));
      
      subOrderRepository.save(suborder);
      return "Saved";
    }
    
    
    // find all
    @GetMapping(path="/getSubOrders")
    public Iterable<SubOrder> getSubOrders() {
      return subOrderRepository.findAll();  // This returns a JSON or XML with the users
    }
    
    
    
    // GET SUBORDERS BELONGING TO A SPECIFIC SUPPLIER
    @GetMapping(path="/getSubOrdersForSupplier")
    public List<SubOrder> getSubOrdersForSupplier (
        @RequestParam String supplierID
    ){
        Supplier supplier = supplierRepository.findById(Integer.parseInt(supplierID)).get(); // .get() is VERY important here as it will return the actual object and not just a reference
        return subOrderRepository.findBySupplier(supplier);
    }
    
}