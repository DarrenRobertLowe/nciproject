
package com.storeii.nciproject.model.locations;

import com.storeii.nciproject.model.deliveries.Driver;
import com.storeii.nciproject.model.County.County;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 *
 * @author Darren Robert Lowe
 */
@Entity // This tells Hibernate to make a table out of this class
public class Location {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    private String locationName;
    
    // FOREIGN KEYS
    @ManyToOne() // this should probably have a (CascadeType.REMOVE) but I'm not certain, since if we have "NOT NULL" set, the database will delete the row.
    @JoinColumn(name = "driver_ID", referencedColumnName = "id")
    private Driver driver;
    
    
    // products
    @OneToMany(
        mappedBy = "location",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    @JsonManagedReference
    private List<County> counties = new ArrayList<>();

    
    
    // GETTERS and SETTERS
    public int getId() {
        return id;
    }
    
    public String getIdAsString() {
        return id.toString();
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }
    
    
    public Driver getDriver() {
        return driver;
    }
    
    public void setDriver(Driver driver) {
        this.driver = driver;
    }
    
    
    public List<County> getCounties() {
        return counties;
    }

    public void setCounties(List<County> counties) {
        this.counties = counties;
    }
}
