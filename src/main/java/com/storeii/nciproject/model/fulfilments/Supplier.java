
package com.storeii.nciproject.model.fulfilments;

import com.storeii.nciproject.model.orders.Order;
import com.storeii.nciproject.model.locations.Location;
import com.storeii.nciproject.model.Address.Address;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.storeii.nciproject.model.products.Product;
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
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 *
 * @author Darren Robert Lowe
 */
@Entity // This tells Hibernate to make a table out of this class
public class Supplier implements Comparable<Supplier> {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    private String storeName;
    
    
    // FOREIGN KEYS
    // addresses
    @OneToOne()
    @JoinColumn(name = "address_ID", referencedColumnName = "id")
    private Address address;
    
    // locations
    @OneToOne()
    @JoinColumn(name = "location_ID", referencedColumnName = "id")
    private Location location;

    
    // products
    @OneToMany(
        mappedBy = "supplier",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    @JsonManagedReference
    private List<Product> products = new ArrayList<>();
    
    
    // Order
    @ManyToMany(fetch = FetchType.LAZY, mappedBy="suppliers", cascade = CascadeType.PERSIST)
    private Set<Order> orders = new HashSet<>();
    
    
    // GETTERS AND SETTERS
    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public void setAddress(Address address) {
        this.address = address;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    // get products
    public List<Product> getProducts() {
        return products;
    }

    // set products
    public void setProducts(List<Product> products) {
        this.products = products;
    }

    
    
    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }
    
    
    public void addOrder(Order order){
        this.orders.add(order);
        order.getSuppliers().add(this);
    }
    
    public void removeOrder(Order order){
        this.orders.remove(order);
        order.getSuppliers().remove(this);
    }

    
    public int compareTo(Supplier other) {
        return (storeName.compareTo(other.getStoreName()));
    }
    
}
