/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.storeii.nciproject.model;

import com.storeii.nciproject.Enums;
import java.util.ArrayList;
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
public class FulfilmentsController {
    @Autowired
    private SubOrderRepository subOrderRepository;
    
    @Autowired
    private SubOrderItemRepository subOrderItemRepository;
    

    @Autowired
    private EntityManager entityManager;
    
    
    
    
    @GetMapping("/fulfilments")
    public ModelAndView showFulfilments(int supplierID) {
        Supplier supplier = entityManager.find(Supplier.class, supplierID); // get the entity
        int orderStatus = Enums.OrderStatus.CONFIRMED.ordinal();           // suppliers only see suborders that haven't yet been fulfilled
        
        
        // get a list deliveries for the specific driver
        List<SubOrder> subOrders = subOrderRepository.findSubOrdersBySupplierAndOrderStatus(supplier, orderStatus);
        ModelAndView mav = new ModelAndView("fulfilments");
        mav.addObject("subOrders", subOrders);
        
        
        // suborder items
        for (SubOrder s : subOrders) {
            List<SubOrderItem> subOrderItems = s.getItems();
            mav.addObject("subOrderItems", subOrderItems);
        }
        
        return mav;
    }
}
