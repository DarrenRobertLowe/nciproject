
package com.storeii.nciproject.model.County;



import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 *
 * @author Darren Robert Lowe
 */

// Spring automatically implements this repository interface in 
// a bean of the same name (lowecase first letter)
public interface CountyRepository extends JpaRepository<County, Integer> {
    County getById(int id);
    County findById(int id);
    
    public County getByCounty(String county);
    public List<County> findByLocation(int location);
}