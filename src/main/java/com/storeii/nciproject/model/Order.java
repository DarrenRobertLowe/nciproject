/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.storeii.nciproject.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
    
    
    // Order Items
    @OneToMany(
            mappedBy = "order",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonManagedReference
    private List<OrderItem> items = new ArrayList<>();
    
    
    // Suppliers
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "SupplierOrders", 
        joinColumns = @JoinColumn(name = "order_ID", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "supplier_ID", referencedColumnName = "id"))
    private Set<Supplier> suppliers = new HashSet<>();
    

    
    
    
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
    
    
    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    
    public Set<Supplier> getSuppliers() {
        return suppliers;
    }

    public void setSuppliers(Set<Supplier> suppliers) {
        this.suppliers = suppliers;
    }
    
    // METHODS
    public void addSupplier(Supplier supplier){
        suppliers.add(supplier);
        supplier.getOrders().add(this);
    }
    
    public void removeSupplier(Supplier supplier){
        this.suppliers.remove(supplier);
        supplier.getOrders().remove(this);
    }
}