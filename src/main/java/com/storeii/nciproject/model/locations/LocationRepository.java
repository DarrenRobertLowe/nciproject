
package com.storeii.nciproject.model.locations;


import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Darren Robert Lowe
 */

// Spring automatically implements this repository interface in 
// a bean of the same name (lowecase first letter)
public interface LocationRepository extends JpaRepository<Location, Integer> {
    Location getById(int id);
}