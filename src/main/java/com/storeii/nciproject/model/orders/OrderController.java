/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.storeii.nciproject.model.orders;

/**
 *
 * @author Main
 */


import com.storeii.nciproject.model.locations.LocationRepository;
import com.storeii.nciproject.model.locations.Location;
import com.storeii.nciproject.model.deliveries.DriverRepository;
import com.storeii.nciproject.model.deliveries.Driver;
import com.storeii.nciproject.model.Customer.CustomerRepository;
import com.storeii.nciproject.model.Customer.Customer;
import com.storeii.nciproject.model.CartItem.CartItemRepository;
import com.storeii.nciproject.model.Address.AddressRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.storeii.nciproject.Enums;
import com.storeii.nciproject.model.orders.orderItems.OrderItem;
import com.storeii.nciproject.model.orders.orderItems.OrderItemRepository;
import com.storeii.nciproject.model.products.Product;
import com.storeii.nciproject.model.products.ProductRepository;
import com.storeii.nciproject.model.subOrders.SubOrder;
import com.storeii.nciproject.model.subOrders.subOrderItems.SubOrderItem;
import com.storeii.nciproject.model.subOrders.subOrderItems.SubOrderItemRepository;
import com.storeii.nciproject.model.fulfilments.Supplier;
import com.storeii.nciproject.model.fulfilments.SupplierRepository;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController // This means that this class is a Controller and @Rest means we don't need to include @ResponseBody
@RequestMapping(path="/webstoredb") // This means URL's start with /webstoredb (after Application path)
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;
    
    @Autowired
    private DriverRepository driverRepository;
    
    @Autowired
    private SubOrderItemRepository subOrderItemRepository;

    @Autowired
    private EntityManager entityManager;
    

    
    
    /**
     * Creates a new order with orderItems and automatically
     * creates new subOrders from the contents.
     * 
     * @param order
     * @param customer
     * @param orderItems
     * @return String confirmation that it's saved
     * 
     */
    @PostMapping(path="/addOrder")
    public String addOrder (
        @RequestParam Order order,
        @RequestParam Customer customer,
        @RequestParam List<OrderItem> orderItems
    ){
        // setup
        order.setOrderStatus(Enums.OrderStatus.CONFIRMED.ordinal());
        order.setCustomer(customer);
        order.setAddress(customer.getAddress());
        Location location = customer.getLocation();
        order.setLocation(location);
        order.setDriver(location.getDriver());
        

        // CREATE SUBORDERS //
        // Create a map for Supplier -> SubOrder
        Map <Supplier, SubOrder> suppliers = new HashMap<>();
        
        // go through the list of orderItems belonging to this order
        for (OrderItem oItem : orderItems) {
            // setup the orderItem and subOrderItem
            Product  product  = oItem.getProduct();
            Supplier supplier = product.getSupplier();
            SubOrder subOrder = null;
            
            /* GET OR CREATE SUBORDER */
            /* If the supplier already is in the hashmap, just grab
             * the corresponding subOrder.
             * Else, add the supplier to the map, create a new subOrder
             * and associate them both in the map.
            */
            if (suppliers.containsKey(supplier)) {
                subOrder = suppliers.get(supplier);                 // use existing subOrder
            } else {
                int status = Enums.OrderStatus.CONFIRMED.ordinal(); // set the subOrder status
                subOrder = new SubOrder(status, order, supplier);   // create the new subOrder
                suppliers.put(supplier, subOrder);                  // add to the map of [suppliers -> suborders]
                
                // Also add the supplier to the order's list of suppliers
                // so it can be used by delivery drivers later.
                order.addSupplier(supplier);
            }
            
            
            // CREATE THE SUBORDER ITEM
            if (subOrder != null) {
                SubOrderItem subItem = new SubOrderItem(subOrder, product, oItem.getQuantity());
                subOrderItemRepository.save(subItem); // Save to make persistent
            } else {
                System.out.println("Error: SubOrder is null, cannot add SubOrderItems");
            }
        } // end of iterating through orderItems
        
        
        // GET THE DATE AND TIME
        // This attempts to get the datetime from the WorldTime server API.
        // If it fails reach the WorldTime server API, it will instead revert
        // to Java's DateTimeLocal() method. It then parses the result into
        // separate "date" and "time" fields.
        String date = "no date";
        String time = "no time";
        
        try {
            String datetime = getDateTime();
            date = parseDate(datetime);
            time = parseTime(datetime);
        } catch (IOException e) {
            System.out.println("Datetime API server is unavailable, using getDateTimeLocal instead.");
            String datetime = getDateTimeLocal();
            date = parseDate(datetime);
            time = parseTime(datetime);
        }
        
        order.setDate(date);
        order.setTime(time);
        
        
        // SAVE THE NEW ORDER AND FINISH
        orderRepository.save(order);
        return "Saved";
    }
    
    
    
    
    // Get datetime from worldtime API server
    @GetMapping(path="/getDateTime")
    public String getDateTime() throws IOException {
        String datetime = "";
        
        //Instantiating the URL class
        URL url = new URL("http://worldtimeapi.org/api/timezone/Europe/Dublin");
 
        // Read the JSON
        JsonNode node = new ObjectMapper().readTree(url);
        
        // Get the value for the datetime field
        node = node.get("datetime");
        
        // get the String of that value
        datetime = node.textValue();
        
        // return the result
        System.out.println("result from datetime server: " + datetime);
        
        // test
        String date = parseDate(datetime);
        String time = parseTime(datetime);
        System.out.println("date: " + date);
        System.out.println("time: " + time);
        
        return datetime;
    }
    
    
    // GET THE DATETIME USING JAVA'S LOCALDATETIME()
    // This is used if retrieving datetime from the
    // worldtime api fails.
    @GetMapping(path="/getDateTimeLocal")
    public String getDateTimeLocal(){
        String datetime = LocalDateTime.now().toString();
        return datetime;
    }
    
    public String parseDate(String datetime) {
        int tPoint = datetime.indexOf('T');
        String date = datetime.subSequence(0, tPoint).toString();
        System.out.println("Date: " + date);
        return date;
    }
    
    public String parseTime(String datetime){
        int tPoint = datetime.indexOf('T');
        int dPoint = datetime.indexOf('.'); // we don't need milliseconds
        String time = datetime.subSequence(tPoint+1, dPoint).toString();
        System.out.println("Time: " + time);
        return time;
    }
    
    
    // find all
    @GetMapping(path="/getOrders")
    public Iterable<Order> getOrders() {
      return orderRepository.findAll();  // This returns a JSON or XML with the users
    }
    
    
    // GET ORDERS BELONGING TO A SPECIFIC DRIVER
    @GetMapping(path="/getOrdersForDriver")
    public List<Order> getOrdersForDriver (
        @RequestParam String driverID
    ){
        Driver driver = driverRepository.findById(Integer.parseInt(driverID)).get(); // .get() is VERY important here as it will return the actual object and not just a reference
        return orderRepository.findByDriver(driver);
    }
    
    
    @GetMapping(path="/deliveries")
    public Iterable<Order> getDeliveries(
        @RequestParam String driverID
    ){
        // get the entity
        Driver driver = entityManager.find(Driver.class, Integer.parseInt(driverID));
        
        return orderRepository.findByDriver(driver);
    }
    
    
    
    
    // GET ORDERS BELONGING TO A SPECIFIC DRIVER THAT ARE READY FOR COLLECTION
    @GetMapping(path="/getOrdersForCollectionByDriver")
    public List<Order> getOrdersForCollectionByDriver (
        @RequestParam String driverID
    ){
        Driver driver = driverRepository.findById(Integer.parseInt(driverID)).get(); // .get() is VERY important here as it will return the actual object and not just a reference
        return orderRepository.findOrdersByDriverAndOrderStatus(driver, 2);
    }
    
    
    
    // MARK A SUBORDER AS READY FOR COLLECTION
    @PostMapping(path="/markOrderAsDelivered")
    public String markOrderAsDelivered(
        @RequestParam String orderID
    ){
       Order order = entityManager.find(Order.class, Integer.parseInt(orderID));
       
       order.setOrderStatus(Enums.OrderStatus.COMPLETE.ordinal());
       
       orderRepository.save(order);
       
       return "";
    }
    
    
    // MARK ORDER AS CANCELLED / RETURNED
    @PostMapping(path="/markOrderAsReturned")
    public String markOrderAsReturned(
        @RequestParam String orderID
    ){
        System.out.println("RUNNING markOrderAsReturned()");
       Order order = entityManager.find(Order.class, Integer.parseInt(orderID));
       
       order.setOrderStatus(Enums.OrderStatus.CANCELLED.ordinal());
       
       orderRepository.save(order);
       
       return "";
    }
}