/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.storeii.nciproject;

import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Main
 */
import com.storeii.nciproject.Address;

// This will be AUTO IMPLEMENTED by Spring into a Bean of the same name with a lowercase first letter
// CRUD refers Create, Read, Update, Delete
public interface AddressRepository extends CrudRepository<Address, Integer> {
    // Spring automatically implements this repository interface in a bean that has the same name
    // but with a lowercase first letter.
}