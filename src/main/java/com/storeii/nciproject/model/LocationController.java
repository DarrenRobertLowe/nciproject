/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.storeii.nciproject.model;

/**
 *
 * @author Main
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController // @Rest means we don't need to include @ResponseBody
@RequestMapping(path="/webstoredb") // This means URL's start with /webstoredb (after Application path)
public class LocationController {
    @Autowired
    private LocationRepository locationRepository;
    
     @Autowired
    private DriverRepository driverRepository;
    
    // Add new
    @PostMapping(path="/addLocation")
    public String addDriver (
        @RequestParam String locationName, 
        @RequestParam String driver_ID)
    {
      Location locale = new Location();
      locale.setLocationName(locationName);
      
      int i = Integer.parseInt(driver_ID);
      locale.setDriver(driverRepository.getById(i));
      
      locationRepository.save(locale);
      return "Saved";
    }
    
    
    // find all
    @GetMapping(path="/getLocations")
    public Iterable<Location> getLocations() {
        return locationRepository.findAll();  // This returns a JSON or XML with the users
    }
}