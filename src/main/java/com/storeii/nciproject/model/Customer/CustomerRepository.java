
package com.storeii.nciproject.model.Customer;


import org.springframework.data.jpa.repository.JpaRepository;


/**
 *
 * @author Darren Robert Lowe
 */

// Spring automatically implements this repository interface in 
// a bean of the same name (lowecase first letter)
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    Customer getById(int id);
}