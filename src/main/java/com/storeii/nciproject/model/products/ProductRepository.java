package com.storeii.nciproject.model.products;


import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Darren Robert Lowe
 */

// Spring automatically implements this repository interface in 
// a bean of the same name (lowecase first letter)
public interface ProductRepository extends JpaRepository<Product, Integer> {
    Product getById(int id);
    Product findByIdentifier(String identifier);
    
    List<Product> getProductsByCategory(String category);
}