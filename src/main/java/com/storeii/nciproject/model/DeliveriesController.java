/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.storeii.nciproject.model;

import com.storeii.nciproject.Enums;
import com.storeii.nciproject.User;
import com.storeii.nciproject.UserPrincipal;
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
        int readyStatus = Enums.OrderStatus.READY.ordinal();        // get the status value as an int
        ModelAndView mav = new ModelAndView("deliveries");
        
        // get the user role for the navbar
        String userRole = webController.getUserRole();
        webController.getNavbar(mav);
        mav.addObject("userType", userRole);
        
        // now we have the supplier object we can check if the User id corresponds.
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserPrincipal userPrincipal = (UserPrincipal)principal;         // make sure you're logged in or you'll get an error!
        boolean valid = false;
        
        if (principal instanceof UserDetails) {
            User user = userPrincipal.getUser();
            
            int driverID = user.getDriver().getId();
            Driver driver = entityManager.find(Driver.class, driverID); // get the entity
            mav.addObject("driver", driver);
            mav.addObject("driverName", driver.getFirstName() + " " + driver.getSurname());
            
            if ((user.getDriver().getId()) == driverID) {
                System.out.println("***** ACCESS GRANTED *****");
                // get a list deliveries for the specific driver
                List<Order> orders = orderRepository.findOrdersByDriverAndOrderStatus(driver, readyStatus);
                
                
                mav.addObject("orders", orders);
                valid = true;
            }
            else {
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
