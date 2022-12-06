/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.storeii.nciproject.model;


import com.storeii.nciproject.model.SubOrder;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Main
 */

// Spring automatically implements this repository interface in 
// a bean of the same name (lowecase first letter)
public interface ProductRepository extends JpaRepository<Product, Integer> {
    Product getById(int id);
    Product findByIdentifier(String identifier);
    
    List<Product> getProductsByCategory(String category);
    
    //List<Product> getProductsByLocation();
}