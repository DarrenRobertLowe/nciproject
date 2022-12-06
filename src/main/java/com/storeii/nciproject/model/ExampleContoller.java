/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.storeii.nciproject.model;

import com.storeii.nciproject.model.Location;
import com.storeii.nciproject.model.LocationRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Main
 */

@Controller
@RequestMapping("/locations")
public class ExampleContoller {
    
    @Autowired
    LocationRepository locationRepo;
    
    @RequestMapping(value = {"/all","/"})
    public String getAll(Model model) {
        List<Location> locations = locationRepo.findAll();
        model.addAttribute("locations", locations);
        model.addAttribute("heading", "This is a heading");
        
        return "locationsView";
    }
    @RequestMapping(value = {"/one","/"})
    public String getFirst(Model model) {
        Location firstLocation = locationRepo.getById(1);
        model.addAttribute("firstLocation", firstLocation);
        model.addAttribute("heading", "This is another heading");
        
        return "firstLocationView";
    }
    
}
