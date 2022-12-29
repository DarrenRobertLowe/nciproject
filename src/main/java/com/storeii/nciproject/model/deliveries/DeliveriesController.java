/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.storeii.nciproject.model.deliveries;

import com.storeii.nciproject.Enums;
import com.storeii.nciproject.User;
import com.storeii.nciproject.UserPrincipal;
import com.storeii.nciproject.model.orders.Order;
import com.storeii.nciproject.model.orders.OrderRepository;
import com.storeii.nciproject.model.website.WebsiteController;
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
public class DeliveriesController {
    @Autowired
    private OrderRepository orderRepository;
    
    
    @Autowired
    private EntityManager entityManager;
    
    @Autowired
    private WebsiteController webController;
    
    
    @GetMapping("/deliveries")
    public ModelAndView showDeliveries() {
        boolean valid;
        int readyStatus = Enums.OrderStatus.READY.ordinal();        // get the status value as an int
        
        // set the correct navbar
        ModelAndView mav = new ModelAndView("deliveries");
        webController.getNavbar(mav);
        
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
            Driver driverObj = user.getDriver();                        // get the driver belonging to the user
            
            if (driverObj == null) {
                System.out.println("No driver found for logged in User!");
                valid = false;
            } else {
                System.out.println("***** ACCESS GRANTED *****");
                valid = true;
            
                int driverID = user.getDriver().getId();
                Driver driver = entityManager.find(Driver.class, driverID); // get the entity
                mav.addObject("driver", driver);
                mav.addObject("driverName", driver.getFirstName() + " " + driver.getSurname());

                String driverAddress = driver.getAddress().getFullAddress();
                mav.addObject("driverAddress", driverAddress);

                List<Order> orders = orderRepository.findOrdersByDriverAndOrderStatus(driver, readyStatus);

                mav.addObject("orders", orders);
                valid = true;
            }
        }
        
        mav.addObject("valid", valid);
        return mav;
    }
    
    
} // end of class
