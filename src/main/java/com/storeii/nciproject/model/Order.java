/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.storeii.nciproject.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author Main
 */
@Entity // This tells Hibernate to make a table out of this class
@Table(name = "Orders") // the table name differs to this object name so we need to tell JPA that.
public class Order {
    /*
    id int UNSIGNED auto_increment PRIMARY KEY,
    orderStatus int UNSIGNED NOT NULL, -- this is a TINYINT to effectively be an enum, but maybe a varchar could be better?
    
    customer_ID int UNSIGNED NOT NULL,
    address_ID int UNSIGNED NOT NULL,
    driver_ID int UNSIGNED,                        // driver must be null because they won't be assigned until later
    location_ID int UNSIGNED,
    FOREIGN KEY (customer_ID) REFERENCES Customer(id),
    FOREIGN KEY (address_ID) REFERENCES Address(id),
    FOREIGN KEY (driver_ID) REFERENCES Driver(id),
    FOREIGN KEY (location_ID) REFERENCES Location(id),
    */
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    
    
    // order status
    // 0: cancelled
    // 1: placed
    // 2: ready for pickup
    // 3: delivering
    // 4: completed
    
    private int orderStatus;
    
    
    // FOREIGN KEYS
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_ID", referencedColumnName = "id")
    private Customer customer;
    
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_ID", referencedColumnName = "id")
    private Address address;
    
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "driver_ID", referencedColumnName = "id")
    private Driver driver; // note driver can be null
    
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "location_ID", referencedColumnName = "id")
    private Location location;
    
    
    /*
    @OneToMany(mappedBy="order")
    @JoinColumn(name = "orderItems", referencedColumnName = "id")
    private Set<OrderItem> orderItems;   //OrderItems orderItems;
    */
    
    /*
    @OneToMany(mappedBy="order") // this is the inverse side (Order is not the owner of the relationship, SubOrder is).
    //@JoinColumn(name = "subOrders", referencedColumnName = "id")
    @JsonIgnore
    private Set<SubOrder> subOrders;
    */
    
    
    // GETTERS and SETTERS
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
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
    
    
    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
    
    
    
    
    /*
    public Set getOrderItems() {
        return orderItems;
    }
    
    public void setOrderItems(Set<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }
    */
    
    /*
    public void addToSubOrders(SubOrder suborder) {
        System.out.println("SubOrder is : " + suborder);
        subOrders.add(suborder);
    }
    
    public Set getSubOrders() {
        return subOrders;
    }
    
    public void setSubOrders(Set<SubOrder> subOrders) {
        this.subOrders = subOrders;
    }
    */
}
