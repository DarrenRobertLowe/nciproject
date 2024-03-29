
package com.storeii.nciproject.model.County;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.storeii.nciproject.model.locations.Location;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author Darren Robert Lowe
 */

@Entity
@Table(name = "Counties")
public class County {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    private String county;
    
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "location")
    private Location location;
    
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
