/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.storeii.nciproject.model;


import com.storeii.nciproject.model.OrderItems;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Main
 */


// Spring automatically implements this repository interface in 
// a bean of the same name (lowecase first letter)
public interface OrderItemsRepository extends CrudRepository<OrderItems, Integer> {
    OrderItems getById(int id);
}