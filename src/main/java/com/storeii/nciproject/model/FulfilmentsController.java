/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.storeii.nciproject.model;

import com.storeii.nciproject.Enums;
import com.storeii.nciproject.User;
import com.storeii.nciproject.UserPrincipal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
        
        
        ModelAndView mav = new ModelAndView("fulfilments");
        
        
        // now we have the supplier object we can check if the User id corresponds.
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserPrincipal userPrincipal = (UserPrincipal)principal; // make sure you're logged in or you'll get an error!
        
        boolean valid = false;
        
        //String username = "None";
        if (principal instanceof UserDetails) {
            User user = userPrincipal.getUser();
            
            if ((user.getSupplier().getId()) == supplierID) {
                System.out.println("***** ACCESS GRANTED *****");
                valid = true;
                
                // get a list deliveries for the specific driver
                List<SubOrder> subOrders = subOrderRepository.findSubOrdersBySupplierAndOrderStatus(supplier, orderStatus);
                mav.addObject("subOrders", subOrders);
                
                // suborder items
                for (SubOrder s : subOrders) {
                    List<SubOrderItem> subOrderItems = s.getItems();
                    mav.addObject("subOrderItems", subOrderItems);
                }
            } else {
                System.out.println("***** ACCESS DENIED *****");
                valid = false;
            }
        } else {
            System.out.println("***** ACCESS DENIED *****");
            valid = false;
        }
        
        mav.addObject("valid", valid);
        return mav;
    }
}
