
package com.storeii.nciproject.model.website;

import com.storeii.nciproject.model.products.Product;
import com.storeii.nciproject.model.products.ProductRepository;
import com.storeii.nciproject.model.orders.OrderRepository;
import com.storeii.nciproject.model.fulfilments.Supplier;
import com.storeii.nciproject.model.fulfilments.SupplierRepository;
import com.storeii.nciproject.model.subOrders.SubOrderRepository;
import com.storeii.nciproject.model.subOrders.subOrderItems.SubOrderItem;
import com.storeii.nciproject.model.subOrders.SubOrder;
import com.storeii.nciproject.model.orders.orderItems.OrderItem;
import com.storeii.nciproject.model.orders.Order;
import com.storeii.nciproject.model.locations.LocationRepository;
import com.storeii.nciproject.model.locations.Location;
import com.storeii.nciproject.model.deliveries.DriverRepository;
import com.storeii.nciproject.model.deliveries.Driver;
import com.storeii.nciproject.model.Customer.Customer;
import com.storeii.nciproject.model.Address.Address;
import com.storeii.nciproject.Enums;
import com.storeii.nciproject.User;
import com.storeii.nciproject.UserService;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Darren Robert Lowe
 */

@Controller
public class SearchController {
    
    @Autowired
    WebsiteController webController;
    
    @Autowired
    ProductRepository productRepository;
    
    @Autowired
    SupplierRepository supplierRepository;
    
    @Autowired
    DriverRepository driverRepository;
    
    @Autowired
    LocationRepository locationRepository;
    
    @Autowired
    OrderRepository orderRepository;
    
    @Autowired
    SubOrderRepository subOrderRepository;
    
    @Autowired
    WebsiteController websiteController;
    
    @Autowired
    UserService userService;
    
    @Autowired
    private WebsiteResourcesService resourcesService;
    
    @GetMapping(value = "/search")
    public @ResponseBody ModelAndView search (
        @RequestParam String searchTerms
    ){
        // create a Model And View to return
        String locationString = "none";
        ModelAndView mav = new ModelAndView();
        websiteController.getNavbar(mav);   // get correct navbar
        Queue results = new LinkedList();
        
        // get rid of punctuation and make the search terms lowercase
        searchTerms = cleanString(searchTerms);
        searchTerms = searchTerms.toLowerCase();
        
        // we need to get the user context so we know how to filter the results
        String userRole = userService.getUserRole();
        
        
        
        // ANONYMOUS USERS
        if (userRole.equalsIgnoreCase("ANONYMOUS")) {
            results = searchProducts(searchTerms, null);
        } else {
            // Signed in
            User user = userService.getUser();
            
            // if it's a customer
            if (user.getCustomer() != null) {
                Location location = locationRepository.getById(user.getCustomer().getLocation().getId());
                locationString = location.getLocationName();
                results = searchProducts(searchTerms, location);
            }
            
            
            // if it's a supplier
            if (user.getSupplier() != null) {
                Supplier supplier = supplierRepository.getById(user.getSupplier().getId());
                results = searchSubOrders(searchTerms, supplier);
                
                mav.addObject("storeName", supplier.getStoreName());
            }
            
            
            // if it's a driver
            if (user.getDriver() != null) {
                Driver driver = driverRepository.getById(user.getDriver().getId());
                results = searchDeliveries(searchTerms, driver);
                
                
                String driverName = driver.getFirstName() + " " + driver.getSurname();
                mav.addObject("driverName", driverName);
                mav.addObject("firstName", driver.getFirstName());
                mav.addObject("surName", driver.getSurname());
            }
        }
        
        
        // return a model and view with the results
        mav.addObject("results",  results);
        mav.addObject("userRole", userRole);
        mav.addObject("location", locationString);
        mav.addObject("searchTerms", searchTerms);
        mav.addObject("image_directory", resourcesService.getImageDirectory() );
        return mav;
    }
    
    
    
    
    
    
    // CLEAN STRING
    // removes anything not a number or letter using regex
    public static String cleanString(String str) {
        str = str.replaceAll("[^a-zA-Z0-9]", "");
        return str;
    }
    
    
    
    
    
    
    
    
    
    // SEARCH PRODUCTS
    public Queue searchProducts(String searchTerms, Location location) {
        Queue results = new LinkedList();                       // used to store direct matches. We could use ArrayList either.
        PriorityQueue extraResults = new PriorityQueue();       // used to sort other results based on compareTo()
        
        // get all the Products
        List<Product> products = productRepository.findAll();
        
        
        // COMPARE
        for(Product product : products) {
            System.out.println("checking: " + product.getProductName());
            
            // DIRECTLY MATCHING ITEM
            String productName = product.getProductName().toLowerCase();
            productName = cleanString(productName);
            String category = product.getCategory();
            String supplier = product.getSupplier().getStoreName();
            String searchString = (productName + category + supplier);
            searchString = searchString.toLowerCase();
            
            if (searchString.contains(searchTerms)) {
                System.out.println(product.getProductName() + " is a direct match!");
                
                // now we'll prevent added products that contain the wrong location.
                // If we're showing all (e.g. not signed in)...
                if (location == null) {
                    System.out.println("Location is null, so adding " + product.getProductName());
                    results.add(product);
                } else {
                    // if we're filtering by location (this could be a checkmark on the search)
                    if (product.getSupplier().getLocation().getId() == location.getId()) {
                        System.out.println("Adding " +product.getProductName() + " belonging to location: "  + location);
                        results.add(product);
                    } else {
                        System.out.println("... but it's not in our location");
                        System.out.println(product.getSupplier().getLocation() + " does not match " + location);
                        System.out.println(product.getSupplier().getLocation().getId() + " does not match " + location.getId());
                    }
                }
                
                // we could now iterate through the results here and
                // add them to a priorityqueue of their own.
            } else {
                // NOT A DIRECTLY MATCHING ITEM //
                // all locations
                if (location == null) { 
                    System.out.println("adding as extra: " + product.getProductName());
                    extraResults.add(product);
                } else {
                    // filterd by location
                    if (product.getSupplier().getLocation() == location) {
                        System.out.println("Adding as extra: " +product.getProductName() + " because it belongs to location: "  + location);
                        extraResults.add(product);
                    }
                }
                
                
            }
        } // end for each Product : products
        
        
        // MERGE DIRECT MATCHES WITH EXTRANEOUS RESULTS
        for (Object r : extraResults) {
            results.add(r);
        }
        
        // RETURN THE RESULTS
        return results;
    }
    
    
    
    
    
    
    
    
    // SEARCH ORDERS
    // Note: the priorityqueue here is meaningless as
    // the comparable method compares Supplier and we're
    // filtering orders by this supplier anyway.
    public Queue searchSubOrders(String searchTerms, Supplier supplier) {
        // create Queues for our results
        System.out.println("SEARCHING FOR SUBORDERS BELONGING TO : " + supplier.getStoreName() );
        
        
        Queue results = new LinkedList();                       // used to store direct matches. We could use ArrayList either.
        PriorityQueue extraResults = new PriorityQueue();       // used to sort other results based on compareTo()
        
        // get all the subOrders
        List<SubOrder> subOrders = subOrderRepository.findAll();
        
        
        // COMPARE
        for(SubOrder subOrder : subOrders) {
            System.out.println("checking: " + subOrder.getId());
            
            // COMBINE ALL SEARCHABLE CRITERIA INTO A SINGLE STRING
            Customer customer = subOrder.getOrder().getCustomer();
            
            // customer name
            String firstName = customer.getFirstName().toLowerCase();
            String surname = customer.getSurname().toLowerCase();
            String customerName = (firstName + surname);
            
            // customer address
            Address address = customer.getAddress();
            String addressLine1 = address.getAddressLine1();
            String addressLine2 = address.getAddressLine2();
            String city = address.getCity();
            String county = address.getDistrict();
            String eircode = address.getPostcode();
            
            String addressString = (addressLine1 + addressLine2 + city + county + eircode);
            
            // subOrder driver
            Driver driver = subOrder.getOrder().getDriver();
            String driverString = (driver.getFirstName() + driver.getSurname());
            
            
            // add the order contents to the searchable string
            String productString = "";
            for(SubOrderItem item : subOrder.getItems()) {
                productString += item.getProduct().getProductName();
            }
            
            
            // subOrderNo
            String subOrderNo = Integer.toString(subOrder.getId());
            // OrderNo
            String orderNo = Integer.toString(subOrder.getOrder().getId());
            
            
            // Order date and time
            String dateString = null;
            String timeString = null;
            String date = subOrder.getOrder().getDate();
            String time = subOrder.getOrder().getTime();
            if (date != null){
                dateString = date;
            }
            
            if (time != null){
                timeString = time;
            }
            
            // finalize the criteria string
            String subOrderString = cleanString(subOrderNo + customerName + addressString + productString + driverString + orderNo + "Date:" + dateString + "Time:" + timeString);
            subOrderString = subOrderString.toLowerCase();
            System.out.println("Searching for query in :: " + subOrderString);
            
            
            
            // DIRECTLY MATCHING ITEM
            if (subOrderString.contains(searchTerms)) {
                System.out.println(subOrder.getId() + " is a direct match!");
                
                if ( (subOrder.getSupplier().getId() == supplier.getId())                   // show only subOrders belonging to this supplier
                && (subOrder.getOrderStatus() == Enums.OrderStatus.CONFIRMED.ordinal()) )   // show only pending subOrders. Remove this if we want to allow historical items
                {
                    System.out.println("The match is for this supplier so we'll add it!");
                    results.add(subOrder);
                } else {
                    System.out.println("... but the supplier doesn't match.");
                }
                
                // we could further sort the items here...
            } else {
                // Do nothing. The HTML will just display a "not found" message.
            }
        } // end for each SubOrder : subOrders
        
        
        // MERGE DIRECT MATCHES WITH EXTRANEOUS RESULTS
        for (Object r : extraResults) {
            results.add(r);
        }
        
        // RETURN THE RESULTS
        return results;
    }
    
    
    
    
    
    
    
    // SEARCH DELIVERIES
    // Note: the priorityqueue here is meaningless as
    // the comparable method compares Drivers and we're
    // filtering orders by this driver anyway.
    public Queue searchDeliveries(String searchTerms, Driver driver) {
        // create Queues for our results
        System.out.println("SEARCHING FOR DELIVERIES BELONGING TO : " + driver.getFirstName() + " " + driver.getSurname() );
        
        
        Queue results = new LinkedList();                       // used to store direct matches. We could use ArrayList either.
        PriorityQueue extraResults = new PriorityQueue();       // used to sort other results based on compareTo()
        
        // get all the subOrders
        List<Order> orders = orderRepository.findAll();
        
        
        /// CREATE SEARCHABLE CRITERIA ///
        // this is where we need to compare our string to the
        // product name (and maybe the suppler store name)
        for(Order order : orders) {
            System.out.println("checking: " + order.getId());
            
            // COMBINE ALL SEARCHABLE CRITERIA INTO A SINGLE STRING
            Customer customer = order.getCustomer();
            
            // customer name
            String firstName    = customer.getFirstName().toLowerCase();
            String surname      = customer.getSurname().toLowerCase();
            String customerName = (firstName + surname);
            
            // customer address
            Address address = customer.getAddress();
            String addressLine1 = address.getAddressLine1();
            String addressLine2 = address.getAddressLine2();
            String city = address.getCity();
            String county = address.getDistrict();
            String eircode = address.getPostcode();
            
            String addressString = (addressLine1 + addressLine2 + city + county + eircode);
            
            // Supplier
            String suppliersString = "";
            for (Supplier s : order.getSuppliers()) {
                suppliersString += s.getStoreName();
                
                // supplier address
                Address supplierAddress = s.getAddress();
                
                suppliersString += supplierAddress.getAddressLine1();
                suppliersString += supplierAddress.getAddressLine2();
                suppliersString += supplierAddress.getCity();
                suppliersString += supplierAddress.getDistrict();
                suppliersString += supplierAddress.getPostcode();
            }
            
            
            // subOrder driver
            String driverString = driver.getFirstName() + driver.getSurname();
            

            // add the order contents to the searchable string
            String productString = "";
            for(OrderItem item : order.getItems()) {
                productString += item.getProduct().getProductName();
            }
            
            
            // OrderNo
            String orderNo = Integer.toString(order.getId());
            
            
            // finalize the criteria string
            String subOrderString = cleanString(customerName + addressString + suppliersString + productString + driverString + orderNo);
            subOrderString = subOrderString.toLowerCase();
            System.out.println("Searching for query in :: " + subOrderString);
            
            // END OF CRITERIA //
            
            
            /// CREATE RESULTS ///
            // DIRECTLY MATCHING ITEM
            if (subOrderString.contains(searchTerms)) {
                System.out.println(order.getId() + " is a direct match!");
                

                
                if ( (order.getDriver().getId() == driver.getId())                       // show only orders belonging to this driver
                && (order.getOrderStatus() == Enums.OrderStatus.READY.ordinal()) )       // show only pending orders. Remove this if we want to allow historical items
                {
                    System.out.println("The match is for this driver so we'll add it!");
                    results.add(order);
                } else {
                    System.out.println("... but the driver doesn't match.");
                }
                
                // we could further sort the items here...
            } else {
                // Do nothing. The HTML will just display a "not found" message.
            }
        } // end for each Order : orders
        
        
        // MERGE DIRECT MATCHES WITH EXTRANEOUS RESULTS
        for (Object r : extraResults) {
            results.add(r);
        }
        
        // RETURN THE RESULTS
        return results;
    }
}
