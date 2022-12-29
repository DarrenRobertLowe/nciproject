/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.storeii.nciproject.model.CartItem;

import com.storeii.nciproject.model.Customer.Customer;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Main
 */
public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
    public List<CartItem> findByCustomer(Customer customer);
    
    public Integer deleteAllByCustomer(Customer customer);
}
