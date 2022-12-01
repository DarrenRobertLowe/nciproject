/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.storeii.nciproject.model;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Main
 */

@RestController
public class RegistrationController {
    
    @Autowired
    LocationRepository locationRepository;
    
    @GetMapping("/locations")
    public ModelAndView listLocations() {
        System.out.println("HELLO");
        
        // get a list deliveries for the specific driver
        List<Location> locations = locationRepository.findAll();
        ModelAndView mav = new ModelAndView("locations");
        mav.addObject("locations", locations);
        
        return mav;
        //return mav;
        
    }
}
