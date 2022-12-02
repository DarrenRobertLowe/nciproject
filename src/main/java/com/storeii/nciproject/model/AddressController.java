/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.storeii.nciproject.model;

/**
 *
 * @author Main
 */
        
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



@RestController // This means that this class is a Controller and @Rest means we don't need to include @ResponseBody
@RequestMapping(path="/webstoredb") // This means URL's start with /webstoredb (after Application path)
public class AddressController {

    /*
    static Address findById(String id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    */
    @Autowired
    // This means to get the bean called repository
    // which is auto-generated by Spring, we will use it to handle the data
    private AddressRepository addressRepository;
    
    
    // Add new
    @PostMapping(path="/addAddress")
    public Integer addAddress (
        @RequestParam String addressLine1,
        @RequestParam String addressLine2,
        @RequestParam String city,
        @RequestParam String district,
        @RequestParam String postcode,
        @RequestParam String country)
    {
        Address address = new Address();
        address.setAddressLine1(addressLine1);
        address.setAddressLine2(addressLine2);
        address.setCity(city);
        address.setDistrict(district);
        address.setPostcode(postcode);
        address.setCountry(country);
        
        
        addressRepository.save(address);
        System.out.println("***** NEW ADDRESS CREATED ******");
        return address.getId();
    }
    
    
    // find all
    @GetMapping(path="/getAddresses")
    public Iterable<Address> getAllAddresses() {
      return addressRepository.findAll();
    }
    
    
    // find by id
    @GetMapping(path="/getAddressesById")
    public Address getById(int l) {
      return addressRepository.getById(l);
    }
}