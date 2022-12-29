/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.storeii.nciproject;

import com.storeii.nciproject.model.Customer.Customer;
import com.storeii.nciproject.model.deliveries.Driver;
import com.storeii.nciproject.model.fulfilments.Supplier;
import java.io.Serializable;
import javax.persistence.CascadeType;
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
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;
    private String userName;
    private String userPass;
    private String role = "USER";   // temporary fix
    
    // FOREIGN KEYS
    // One user can have one of each of the following,
    // so for now these are one-to-one unidirectional.
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer", referencedColumnName = "id")
    private Customer customer;
    
    // Unidirectional
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "driver", referencedColumnName = "id")
    private Driver driver;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "supplier", referencedColumnName = "id")
    private Supplier supplier;

    
    
    
    public Integer getId() {
        return Id;
    }

    public void setId(Integer Id) {
        this.Id = Id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPass() {
        return userPass;
    }

    public void setUserPass(String userPass) {
        this.userPass = userPass;
    }

    
    public String getRole() {
        return role;
    }
    
    public void setRole(String role) {
        this.role = role;
    }
    
    
    // FOREIGN KEYS
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }
    
}
