/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.storeii.nciproject.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
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
@Table(name="SubOrder")
public class SubOrder {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    private int orderStatus;
    
    // FOREIGN KEYS
    @ManyToOne // we are the owning side of the relationship
    @JoinColumn(name = "order_ID", referencedColumnName = "id")
    private Order order;
    
    @ManyToOne()
    @JoinColumn(name = "supplier_ID", referencedColumnName = "id")
    private Supplier supplier;
    
    
    // SubOrder Items
    @OneToMany(
            mappedBy = "subOrder",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonManagedReference
    // this will be used to store the list of items
    private List<SubOrderItem> items = new ArrayList<>();

    public void setItems(List<SubOrderItem> items) {
        this.items = items;
    }
    
    
    
    public List<SubOrderItem> getItems(){
        return items;
    }
    
    
    
    // GETTERS and SETTERS
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }
    
    
    // bidirectionality with SubOrderItem
    public void addItem(SubOrderItem item){
        items.add(item);
        item.setSubOrder(this);
    }
    
    public void removeItem(SubOrderItem item){
        items.remove(item);
        item.setSubOrder(null);
    }
    
    
     /// CONSTRUCTORS
    public SubOrder(){
    }
    
    public SubOrder(int orderStatus, Order order_ID, Supplier supplier_ID) {
        this.orderStatus = orderStatus;
        this.order       = order_ID;
        this.supplier    = supplier_ID;
    }
}
