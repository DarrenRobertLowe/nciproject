/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.storeii.nciproject.model;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Main
 */
import com.storeii.nciproject.model.Address;


// This will be AUTO IMPLEMENTED by Spring into a Bean of the same name with a lowercase first letter
// CRUD refers Create, Read, Update, Delete
public interface AddressRepository extends CrudRepository<Address, Integer> {
    // Spring automatically implements this repository interface in a bean that has the same name
    // but with a lowercase first letter.
    Address getById(Long id);
}