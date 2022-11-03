/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.storeii.nciproject;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author Main
 */
@Entity // This tells Hibernate to make a table out of this class
@Table(name = "Orders") // the table name differs to this object name so we need to tell JPA that.
public class Order {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private int orderStatus;
    
    
    // FOREIGN KEYS
    @OneToOne()
    @JoinColumn(name = "address_ID", referencedColumnName = "id")
    private Address address;
    
    @OneToOne()
    @JoinColumn(name = "customer_ID", referencedColumnName = "id")
    private Customer customer;
    
    // note driver can be null
    @OneToOne()
    @JoinColumn(name = "driver_ID", referencedColumnName = "id")
    private Driver driver;
    
    
    // GETTERS and SETTERS
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }
    
    public int getOrderStatus() {
        return orderStatus;
    }
    
    public Driver getDriver() {
        return driver;
    }
    
    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
