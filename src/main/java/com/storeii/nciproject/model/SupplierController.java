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
import javax.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController // @Rest means we don't need to include @ResponseBody
@RequestMapping(path="/webstoredb") // This means URL's start with /webstoredb (after Application path)
public class SupplierController {
    @Autowired
    private SupplierRepository supplierRepository;
    
    @Autowired
    private AddressRepository addressRepository;
    
    
    @Autowired
    private OrderRepository orderRepository;
    
    
    @Autowired
    private LocationRepository locationRepository;
    
    @Autowired
    private EntityManager entityManager;
    
    // Add new
    @PostMapping(path="/addSupplier") // Map ONLY POST Requests
    public Supplier addSupplier (
        @RequestParam String storeName,
        @RequestParam String address_ID,
        @RequestParam String location_ID
        )
    {
        Supplier supplier = new Supplier();
        supplier.setStoreName(storeName);
        
        
        int address  = Integer.parseInt(address_ID);
        int locale = Integer.parseInt(location_ID);
        
        supplier.setAddress(addressRepository.getById(address));
        supplier.setLocation(locationRepository.getById(locale));
        
        supplierRepository.save(supplier);
        return supplier;
    }
    
    
    // find all
    @GetMapping(path="/getSuppliers")
    public Iterable<Supplier> getSuppliers() {
        return supplierRepository.findAll();  // This returns a JSON or XML with the users
    }
    
    
    // GET ORDERS BELONGING TO A SPECIFIC DRIVER
    @GetMapping(path="/getSuppliersByOrder")
    public List<Supplier> getSuppliersByOrder (
        @RequestParam String orderID
    ){
        Order order = entityManager.find(Order.class, Integer.parseInt(orderID));//orderRepository.findById(Integer.parseInt(orderID)).get(); // .get() is VERY important here as it will return the actual object and not just a reference
        return supplierRepository.findByOrders(order);
    }
}