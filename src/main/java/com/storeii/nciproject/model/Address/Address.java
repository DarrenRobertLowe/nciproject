
package com.storeii.nciproject.model.Address;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author Darren Robert Lowe
 */
@Entity // This tells Hibernate to make a table out of this class
public class Address implements Serializable {
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private int id;
  private String addressLine1;
  private String addressLine2;
  private String city;
  private String district;
  private String postcode;
  private String country;
  
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getFullAddress() {
        // Returns the full address in a single 
        // string separated by spaces.
        // Used for Google Maps
        return (
            addressLine1 + " " +
            addressLine2 + " " +
            city + " " +
            district + " " +
            postcode + " " +
            country
            );
    }
}
