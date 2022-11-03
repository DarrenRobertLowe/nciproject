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

/**
 *
 * @author Main
 */
@Entity // This tells Hibernate to make a table out of this class
public class SubOrder {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private int orderStatus;
    
    
    // FOREIGN KEYS
    @OneToOne()
    @JoinColumn(name = "order_ID", referencedColumnName = "id")
    private Address order;
    
    @OneToOne()
    @JoinColumn(name = "supplier_ID", referencedColumnName = "id")
    private Customer supplier;
    
    
    // GETTERS and SETTERS
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Address getOrder() {
        return order;
    }

    public void setOrder(Address order) {
        this.order = order;
    }

    public Customer getSupplier() {
        return supplier;
    }

    public void setSupplier(Customer supplier) {
        this.supplier = supplier;
    }
    
    
    
}
