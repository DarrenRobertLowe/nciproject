/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.storeii.nciproject.model.orders.orderItems;



import com.storeii.nciproject.model.orders.Order;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.ArrayList;

/**
 *
 * @author Darren Robert Lowe
 */


// Spring automatically implements this repository interface in 
// a bean of the same name (lowecase first letter)
public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
    OrderItem getById(int id);
    OrderItem findById(int id);
    
    public List<OrderItem> findByQuantity(int quantity);
    
    
    public ArrayList<OrderItem> findByOrder(Order order);
    
    
    //public List<OrderItem> findOrderItemsByOrder(Order order);
    //public OrderItem addOrderItem(String orderId, String productID, String quantity, String unitPrice);
}