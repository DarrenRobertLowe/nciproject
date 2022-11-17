/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.storeii.nciproject.model;

import com.storeii.nciproject.Enums;
import java.util.List;
import javax.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;




/**
 *
 * @author Main
 */

@RestController
public class DeliveriesController {
    @Autowired
    private OrderRepository orderRepository;
    
    
    @Autowired
    private EntityManager entityManager;
    
    
    @GetMapping("/deliveries")
    public ModelAndView showDeliveries(int driverID) {
        Driver driver = entityManager.find(Driver.class, driverID);         // get the entity
        int readyStatus = 2;// Enums.OrderStatus.READY.ordinal();           // get the status value as an int
        
        // get a list deliveries for the specific driver
        List<Order> orders = orderRepository.findOrdersByDriverAndOrderStatus(driver, readyStatus);
        
        ModelAndView mav = new ModelAndView("driver-deliveries");
        
        mav.addObject("orders", orders);
        
        return mav;
    }
}
