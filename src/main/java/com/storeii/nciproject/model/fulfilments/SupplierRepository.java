/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.storeii.nciproject.model.fulfilments;


import com.storeii.nciproject.model.orders.Order;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Darren Robert Lowe
 */

// Spring automatically implements this repository interface in 
// a bean of the same name (lowecase first letter)
public interface SupplierRepository extends JpaRepository<Supplier, Integer> {
    Supplier getById(int id);
    
    
    List<Supplier> findByOrders(Order order);
}