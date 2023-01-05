
package com.storeii.nciproject;

import com.storeii.nciproject.model.Address.AddressController;
import com.storeii.nciproject.model.County.County;
import com.storeii.nciproject.model.County.CountyRepository;
import com.storeii.nciproject.model.Customer.Customer;
import com.storeii.nciproject.model.Customer.CustomerController;
import com.storeii.nciproject.model.deliveries.Driver;
import com.storeii.nciproject.model.deliveries.DriverController;
import com.storeii.nciproject.model.locations.Location;
import com.storeii.nciproject.model.locations.LocationRepository;
import com.storeii.nciproject.model.fulfilments.Supplier;
import com.storeii.nciproject.model.fulfilments.SupplierController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


/**
 *
 * @author Darren Robert Lowe
 */

@EnableWebSecurity
@Controller
//@RequestMapping(path="/webstoredb") // This means URL's start with /webstoredb (after Application path)
public class UserController {
    // AUTOWIRES //
    
    @Autowired
    UserRepository userRepository;
    
    
    @Autowired
    private CustomerController customerController;
    
    
    @Autowired
    AddressController addressController;
    
    @Autowired
    SupplierController supplierController;
    
    @Autowired
    DriverController driverController;
    
    @Autowired
    LocationRepository locationRepository;
    
    @Autowired
    PasswordEncoder passwordEncoder;    // this loads bCryptEncoder via the CustomSecurityConfig class
    
    @Autowired
    CountyRepository countyRepository;
    
    
    
    // Login form
    @RequestMapping("/login.html")
    public String login() {
      return "login.html";
    }

    // Login form with error
    @RequestMapping("/login-error.html")
    public String loginError(Model model) {
      model.addAttribute("loginError", true);
      return "login.html";
    }
    
    
    
    // find all customers
    @GetMapping(path="/getUsers")
    public Iterable<User> getUsers() {
        return userRepository.findAll();  // This returns a JSON or XML with the users
    }
    
    
    
    
    
    // CUSTOMER
    // Adding a new Customer
    @PostMapping(path="/addUserCustomer")
    public String addUser (
        @RequestParam String firstName,
        @RequestParam String surname,
        @RequestParam String userName,
        @RequestParam String userPass,
        @RequestParam String addressLine1,
        @RequestParam String addressLine2,
        @RequestParam String city,
        @RequestParam String district,
        @RequestParam String postcode,
        @RequestParam String country
    ){
        User user = new User();
        user.setUserName(userName);
        user.setRole("CUSTOMER");
        
        // encrypt password
        String encryptedPass = passwordEncoder.encode(userPass);
        user.setUserPass(encryptedPass);
        System.out.println("********* encryptedPass is " + encryptedPass);
        
        // CREATE ADDRESS
        /* At this point we need to create a new Customer object.
        * Before we can do that we need to create the address.
        * After that we can assign new Customer to the User.
        */
        // AddressController ac = new AddressController();
        Integer addressInt = addressController.addAddress(addressLine1, addressLine2, city, district, postcode, country);
        System.out.println("******* RETURNED ADDRESS ID IS : " + addressInt);
        String addressId = addressInt.toString(); // get the addressId as a String for creating the Customer
        
        // DERIVE LOCATION FROM COUNTY
        County county = countyRepository.getByCounty(district);
        Location location = county.getLocation();
        String locationId = Integer.toString(location.getId());
        
        // CREATE CUSTOMER
        Customer newCustomer = customerController.addCustomer(firstName, surname, userName, encryptedPass, addressId, locationId);
        
        // SET THE CUSTOMER
        user.setCustomer(newCustomer);
        
        // SAVE THE USER
        userRepository.save(user);
        return "registration-success";
    }
    
    
    
    
    
    // SUPPLIER
    // Adding a new Supplier
    @GetMapping(path="/addUserSupplier")
    public String addSupplier (
        @RequestParam String userName,
        @RequestParam String userPass,
        //@RequestParam String role,
        @RequestParam String storeName,
        @RequestParam String location,
        @RequestParam String addressLine1,
        @RequestParam String addressLine2,
        @RequestParam String city,
        @RequestParam String district,
        @RequestParam String postcode,
        @RequestParam String country
    ){
        User user = new User();
        user.setUserName(userName);
        user.setRole("SUPPLIER");
        
        // encrypt password
        String encryptedPass = passwordEncoder.encode(userPass);
        user.setUserPass(encryptedPass);
        
        
        /********
        * At this point we need to create a new Supplier object.
        * Before we can do that we need to create the address.
        * After that we can assign supplierId to the User.
        ********/
        
        // CREATE ADDRESS
        // AddressController ac = new AddressController();
        Integer addressInt = addressController.addAddress(addressLine1, addressLine2, city, district, postcode, country);
        System.out.println("******* RETURNED ADDRESS ID IS : " + addressInt);
        String addressId = addressInt.toString();// = ((Integer)address.getId()).toString(); // get the addressId as a String for creating the Customer
        
        // CREATE SUPPLIER
        //CustomerController cc = new CustomerController();
        System.out.println("********* encryptedPass is " + encryptedPass);
        Supplier supplier = supplierController.addSupplier(storeName, addressId, location);
        
        // SET THE CUSTOMER
        user.setSupplier(supplier);
        
        // SAVE THE USER
        userRepository.save(user);
        return "registration-success";
    }
    
    
    
    
    
    
    // DRIVER
    // Adding a new Driver
    @GetMapping(path="/addUserDriver")
    public String addDriver (
        @RequestParam String firstName,
        @RequestParam String surname,
        @RequestParam String userName,
        @RequestParam String userPass
    ){
        User user = new User();
        user.setUserName(userName);
        user.setRole("DRIVER");
        
        // encrypt password
        String encryptedPass = passwordEncoder.encode(userPass);
        user.setUserPass(encryptedPass);
        
        
        /********
        * At this point we need to create a new Driver object.
        * After that we can assign driverId to the User.
        ********/
        
        // CREATE DRIVER
        //CustomerController cc = new CustomerController();
        System.out.println("********* encryptedPass is " + encryptedPass);
        Driver driver = driverController.addDriver(firstName, surname);
        
        // SET THE CUSTOMER
        user.setDriver(driver);
        
        // SAVE THE USER
        userRepository.save(user);
        return "registration-success";
    }
}
