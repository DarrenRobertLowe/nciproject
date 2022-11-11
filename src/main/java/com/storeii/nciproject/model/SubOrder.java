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
import javax.persistence.Table;

/**
 *
 * @author Main
 */
@Entity // This tells Hibernate to make a table out of this class
@Table(name="SubOrder")
public class SubOrder {
    /*
    id int UNSIGNED auto_increment PRIMARY KEY,
    orderStatus TINYINT UNSIGNED NOT NULL,
    order_ID int UNSIGNED NOT NULL,
    supplier_ID smallint UNSIGNED NOT NULL,
    FOREIGN KEY (order_ID) REFERENCES Orders(id),
    FOREIGN KEY (supplier_ID) REFERENCES Supplier(id)
    */
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
    
    
     /// CONSTRUCTORS
    public SubOrder(){
    }
    
    public SubOrder(int orderStatus, Order order_ID, Supplier supplier_ID) {
        this.orderStatus = orderStatus;
        this.order       = order_ID;
        this.supplier    = supplier_ID;
    }
}
