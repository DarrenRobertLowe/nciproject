/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.storeii.nciproject.model.subOrders.subOrderItems;


import com.storeii.nciproject.model.subOrders.SubOrder;
import com.storeii.nciproject.model.subOrders.subOrderItems.SubOrderItem;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Main
 */

// Spring automatically implements this repository interface in 
// a bean of the same name (lowecase first letter)
public interface SubOrderItemRepository extends CrudRepository<SubOrderItem, Integer> {
    SubOrderItem getById(int id);
    
    
    public List<SubOrderItem> findBySubOrder(SubOrder subOrder);
}