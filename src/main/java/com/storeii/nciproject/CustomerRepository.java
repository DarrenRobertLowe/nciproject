/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.storeii.nciproject;

/**
 *
 * @author Main
 */

import org.springframework.data.repository.CrudRepository;

import com.storeii.nciproject.Customer;

// This will be AUTO IMPLEMENTED by Spring into a Bean called customerRepository
// CRUD refers Create, Read, Update, Delete
public interface CustomerRepository extends CrudRepository<Customer, Integer> {
    // Spring automatically implements this repository interface in a bean that has the same name
    // (with a change in the case — it is called customerRepository), with a lowercase first letter.
    
    
}

