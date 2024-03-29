package com.storeii.nciproject.model.orders;


import com.storeii.nciproject.model.locations.Location;
import com.storeii.nciproject.model.deliveries.Driver;
import com.storeii.nciproject.model.Customer.Customer;
import com.storeii.nciproject.model.Address.Address;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.storeii.nciproject.model.orders.orderItems.OrderItem;
import com.storeii.nciproject.model.fulfilments.Supplier;
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
import javax.persistence.Table;

/**
 *
 * @author Darren Robert Lowe
 */
@Entity // This tells Hibernate to make a table out of this class
@Table(name = "Orders") // the table name differs to this object name so we need to tell JPA that.
public class Order {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    private int orderStatus;
    private String date;
    private String time;
    
    
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    
    
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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