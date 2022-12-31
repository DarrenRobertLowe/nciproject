package com.storeii.nciproject.model.website;

import org.springframework.stereotype.Service;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Darren Robert Lowe
 */
@Service
public class WebsiteResourcesService {
    String imageDirectory = "../assets/img/products/";
    
    
    public String getImageDirectory() {
        return imageDirectory;
    }
}
