/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.storeii.nciproject.model;

import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Main
 */
import com.storeii.nciproject.model.OrderItem;

// This will be AUTO IMPLEMENTED by Spring into a Bean called customerRepository
// CRUD refers Create, Read, Update, Delete
public interface OrderItemRepository extends CrudRepository<OrderItem, Integer> {
    // Spring automatically implements this repository interface in a bean that has the same name
    // (with a change in the case — it is called customerRepository), with a lowercase first letter.
}