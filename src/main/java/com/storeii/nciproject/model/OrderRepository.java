/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.storeii.nciproject.model;
import com.storeii.nciproject.model.Order;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Main
 */

// Spring automatically implements this repository interface in 
// a bean of the same name (lowecase first letter)
public interface OrderRepository extends CrudRepository<Order, Integer> {
    Order getById(int id);
    public List<Order> findByDriver(Driver driver);

    /*
    @Query(value = "FROM Order WHERE orderStatus=3 and driver_ID = :driver_ID")
    List<Order> getOrdersForCollectionByDriver(
            @Param("driver_ID") String driver//List<Driver> drivers
    );
    
    @Query(value = "FROM Order WHERE orderStatus=2")
    public List<Order> findReadyOrdersByDriver(Driver driver);
    */
    
    public List<Order> findOrdersByDriverAndOrderStatus(Driver driver, int orderStatus);
    
    
}