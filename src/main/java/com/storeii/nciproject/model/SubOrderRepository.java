/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.storeii.nciproject.model;

import com.storeii.nciproject.model.SubOrder;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Main
 */

// Spring automatically implements this repository interface in 
// a bean of the same name (lowecase first letter)
public interface SubOrderRepository extends JpaRepository<SubOrder, Integer> {
    SubOrder getById(int id);
    
    public List<SubOrder> findBySupplier(Supplier supplier);
    
    public List<SubOrder> findByOrder(Order order);
    
    public List<SubOrder> findSubOrdersBySupplierAndOrderStatus(Supplier supplier, int orderStatus);
}