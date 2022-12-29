/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.storeii.nciproject.model.County;



import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.storeii.nciproject.model.orders.orderItems.OrderItem;
import java.util.ArrayList;

/**
 *
 * @author Main
 */


// Spring automatically implements this repository interface in 
// a bean of the same name (lowecase first letter)
public interface CountyRepository extends JpaRepository<County, Integer> {
    County getById(int id);
    County findById(int id);
    
    public County getByCounty(String county);
    public List<County> findByLocation(int location);
}