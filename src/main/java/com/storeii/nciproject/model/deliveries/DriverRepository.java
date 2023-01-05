/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.storeii.nciproject.model.deliveries;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Darren Robert Lowe
 */

// Spring automatically implements this repository interface in 
// a bean of the same name (lowecase first letter)
public interface DriverRepository extends JpaRepository<Driver, Integer> {
    Driver getById(int id);
}