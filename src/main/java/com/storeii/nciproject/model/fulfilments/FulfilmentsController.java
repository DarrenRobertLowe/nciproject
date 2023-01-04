/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.storeii.nciproject.model.fulfilments;

import com.storeii.nciproject.model.subOrders.SubOrderRepository;
import com.storeii.nciproject.model.subOrders.subOrderItems.SubOrderItemRepository;
import com.storeii.nciproject.model.subOrders.subOrderItems.SubOrderItem;
import com.storeii.nciproject.model.subOrders.SubOrder;
import com.storeii.nciproject.Enums;
import com.storeii.nciproject.User;
import com.storeii.nciproject.UserPrincipal;
import com.storeii.nciproject.model.website.WebsiteController;
import com.storeii.nciproject.model.website.WebsiteResourcesService;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;




/**
 *
 * @author Darren Robert Lowe
 */

@RestController
public class FulfilmentsController {
    @Autowired
    private SubOrderRepository subOrderRepository;
    
    @Autowired
    private SubOrderItemRepository subOrderItemRepository;
    
    @Autowired
    private EntityManager entityManager;
    
    @Autowired
    private WebsiteController webController;
    
    @Autowired
    private WebsiteResourcesService resourcesService;
    
    
    @GetMapping("/fulfilments")
    public ModelAndView showFulfilments() {
        // set the correct navbar
        ModelAndView mav = new ModelAndView("fulfilments");
        webController.getNavbar(mav);
        
        boolean valid;
        
        // get the user principal
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        
        // verify the user
        if (principal == "anonymousUser") {
            System.out.println("Attempted access of secure location by anonymous user!");
            valid = false;
        } else {
            // we're logged in
            UserPrincipal userPrincipal = (UserPrincipal)principal;     // get the logged in user's details
            User user = userPrincipal.getUser();                        // get the logged in user
            Supplier supplierObj = user.getSupplier();                  // get the supplier belonging to the user
            
            
            if (supplierObj == null) {
                System.out.println("No supplier found for logged in User!");
                valid = false;
            } else {
                System.out.println("***** ACCESS GRANTED *****");
                valid = true;
                
                
                // EntityManager will return a different object to our supplierObj, so we need
                // to explicitely return that using the id, which is the same for both.
                Supplier supplier = entityManager.find(Supplier.class, supplierObj.getId());    // get the supplier entity. this might be redundant)
                
                String storeName = supplier.getStoreName();
                mav.addObject("supplier", supplier);
                mav.addObject("storeName", storeName);
                
                int orderStatus = Enums.OrderStatus.CONFIRMED.ordinal();    // suppliers should only see suborders that haven't yet been fulfilled
                
                // get a list deliveries for the specific driver
                List<SubOrder> subOrders = subOrderRepository.findSubOrdersBySupplierAndOrderStatus(supplier, orderStatus);
                mav.addObject("subOrders", subOrders);
            }
        }
        
        
        // CREATE THE MODEL AND GO TO PAGE
        mav.addObject("valid", valid);
        mav.addObject("image_directory",resourcesService.getImageDirectory());
        return mav;
    }
}
