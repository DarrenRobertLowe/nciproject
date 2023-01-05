package com.storeii.nciproject.model.website;

import org.springframework.stereotype.Service;


/**
 *
 * @author Darren Robert Lowe
 */
@Service
public class WebsiteResourcesService {
    String imageDirectory = "../assets/img/products/";
    Double deliveryCost = 6.0;
    
    
    public String getImageDirectory() {
        return imageDirectory;
    }
    
    public Double getDeliveryCost() {
        return deliveryCost;
    }
}   
