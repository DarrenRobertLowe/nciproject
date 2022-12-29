/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.storeii.nciproject.model.County;

/**
 *
 * @author Main
 */

import com.storeii.nciproject.model.locations.Location;
import com.storeii.nciproject.model.locations.LocationRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.bind.annotation.RestController;


@RestController // This means that this class is a Controller and @Rest means we don't need to include @ResponseBody
@RequestMapping(path="/webstoredb") // This means URL's start with /webstoredb (after Application path)
public class CountyController {
    @Autowired
    private CountyRepository countyRepository;
    
    @Autowired
    private LocationRepository locationRepository;
    
    
    // Add new
    @PostMapping(path="/addCounty") // Map ONLY POST Requests
    public County addCounty (
        @RequestParam String name,
        @RequestParam int locationId
    ){
      County county = new County();
      
      //county name
      county.setCounty(name);
      
      //location
      Location location = locationRepository.getById(locationId);
      county.setLocation(location);
      

      countyRepository.save(county);
      
      System.out.println("added new couunty: " + county.getCounty());
      
      return county;
    }
    
    
    
    // find all
    @GetMapping(path="/getCounties")
    public Iterable<County> getCounties() {
        return countyRepository.findAll();  // This returns a JSON or XML with the users
    }
    
   
    // find by location
    @GetMapping(path="/findCountiesByLocation")
    public Iterable<County> findByLocation(@RequestParam String location) {
        return countyRepository.findByLocation(Integer.parseInt(location));  // This returns a JSON or XML with the users
    }
}