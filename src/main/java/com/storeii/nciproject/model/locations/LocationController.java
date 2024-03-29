package com.storeii.nciproject.model.locations;

/**
 *
 * @author Darren Robert Lowe
 */

import com.storeii.nciproject.model.deliveries.DriverRepository;
import com.storeii.nciproject.model.County.County;
import java.util.List;
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
    public String addLocation (
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
        return locationRepository.findAll();
    }
    
    
    @GetMapping(path="/getLocationCounties")
    public String getLocationCounties(@RequestParam int locationId) {
        Location location = locationRepository.getById(locationId);
        String header = ("COUNTIES IN " + location.getLocationName() + ": ");
        
        String countiesString = header;
        
        List<County> counties = location.getCounties();
        
        for(County c :counties) {
            countiesString += " ";
            countiesString += c.getCounty();
        }
        countiesString += ".";
        
        System.out.println(countiesString);
        return countiesString;
    }
    
}