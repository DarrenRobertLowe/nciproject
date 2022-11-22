/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.storeii.nciproject.model;
import com.storeii.nciproject.model.Order;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Main
 */

// Spring automatically implements this repository interface in 
// a bean of the same name (lowecase first letter)
public interface OrderRepository extends
        JpaRepository<Order, Integer>
        //OrderRepositoryCustom
{
    Order getById(int id);
    public List<Order> findByDriver(Driver driver);

    
    public List<Order> findOrdersByDriverAndOrderStatus(Driver driver, int orderStatus);
    
    //@Override
    //public ArrayList<OrderItem> findOrderItems(Order order);
    //public String addOrder (String orderId, String customer_ID);
}