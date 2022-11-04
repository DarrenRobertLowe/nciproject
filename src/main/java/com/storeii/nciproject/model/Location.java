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
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

/**
 *
 * @author Main
 */
@Entity // This tells Hibernate to make a table out of this class
public class Location {
    /*
    id smallint UNSIGNED auto_increment PRIMARY KEY,
    driver_ID smallint UNSIGNED,
    FOREIGN KEY (driver_ID) REFERENCES Driver(id)
    */
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;
    
    // FOREIGN KEYS
    @ManyToOne() // this should probably have a (CascadeType.REMOVE) but I'm not certain, since if we have "NOT NULL" set, the database will delete the row.
    @JoinColumn(name = "driver_ID", referencedColumnName = "id")
    private Driver driver;
    
    
    
    // GETTERS and SETTERS
    public long getId() {
        return id;
    }
    
    public void setId(long id) {
        this.id = id;
    }
    
    public Driver getDriver() {
        return driver;
    }
    
    public void setDriver(Driver driver) {
        this.driver = driver;
    }
}
