/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.storeii.nciproject.model;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import javax.persistence.Column;
/**
 *
 * @author Main
 */
@Embeddable
public class SupplierOrders implements Serializable {
    @Column(name = "supplier_id")
    Long supplierId;

    @Column(name = "order_id")
    Long orderId;
}
