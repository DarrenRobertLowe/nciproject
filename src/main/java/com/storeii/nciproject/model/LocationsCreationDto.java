/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.storeii.nciproject.model;

import java.util.List;

/**
 *
 * @author Main
 */
public class LocationsCreationDto {
    private List<Location> locations;
    
    public int getSize() {
        return locations.size();
    }
    
    public void addBook(Location location) {
        this.locations.add(location);
    }
}
