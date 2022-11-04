/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.storeii.nciproject.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

/**
 *
 * @author Main
 */
@Entity // This tells Hibernate to make a table out of this class
public class Supplier {
    /*
    id smallint UNSIGNED auto_increment PRIMARY KEY,
    storeName varchar(100) NOT NULL,
    address_ID int UNSIGNED,
    location_ID smallint UNSIGNED,
    FOREIGN KEY (address_ID) REFERENCES Address(id),
    FOREIGN KEY (location_ID) REFERENCES Location(id)
    */
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String storeName;
    
    
    // FOREIGN KEYS
    @OneToOne()
    @JoinColumn(name = "address_ID", referencedColumnName = "id")
    private Address address;
    
    @OneToOne()
    @JoinColumn(name = "location_ID", referencedColumnName = "id")
    private Location location;
    
    
    // GETTERS AND SETTERS
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStoreName() {
        return storeName;
    }
    
    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }
    
    public Address getAddress() {
        return address;
    }
    
    public Location getLocation() {
        return location;
    }
}
