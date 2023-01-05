package com.storeii.nciproject.model.subOrders;

/**
 *
 * @author Darren Robert Lowe
 */


import com.storeii.nciproject.model.orders.Order;
import com.storeii.nciproject.Enums.OrderStatus;
import com.storeii.nciproject.model.orders.OrderRepository;
import com.storeii.nciproject.model.fulfilments.Supplier;
import com.storeii.nciproject.model.fulfilments.SupplierRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController // This means that this class is a Controller and @Rest means we don't need to include @ResponseBody
@RequestMapping(path="/webstoredb") // This means URL's start with /webstoredb (after Application path)
public class SubOrderController {
    @Autowired
    private SubOrderRepository subOrderRepository;
    
    @Autowired
    private OrderRepository orderRepository;
    
    @Autowired
    private SupplierRepository supplierRepository;
    

    
    // Add new
    @PostMapping(path="/addSubOrder")
    public String addSubOrder (
        @RequestParam String orderStatus,      // this should be set to 1 for new orders
        @RequestParam String order_ID,
        @RequestParam String supplier_ID
    )
    {
      SubOrder suborder = new SubOrder();
      
      
      // set fields
      int status = Integer.parseInt(orderStatus);
      int order = Integer.parseInt(order_ID);
      int supplier = Integer.parseInt(supplier_ID);
      
      suborder.setOrderStatus(status);
      
      Order parentOrder = orderRepository.getById(order);
      suborder.setOrder(parentOrder);
      suborder.setSupplier(supplierRepository.getById(supplier));
      
      // this is so we can look at Order to find the SubOrders.
      //parentOrder.addSubOrder(suborder);
      
      subOrderRepository.save(suborder);
      return "Saved";
    }
    
    
    // find all
    @GetMapping(path="/getSubOrders")
    public Iterable<SubOrder> getSubOrders() {
      return subOrderRepository.findAll();  // This returns a JSON or XML with the users
    }
    
    
    
    // GET SUBORDERS BELONGING TO A SPECIFIC SUPPLIER
    @GetMapping(path="/getSubOrdersForSupplier")
    public List<SubOrder> getSubOrdersForSupplier (
        @RequestParam String supplierID
    ){
        Supplier supplier = supplierRepository.findById(Integer.parseInt(supplierID)).get(); // .get() is VERY important here as it will return the actual object and not just a reference
        return subOrderRepository.findBySupplier(supplier);
    }
    
    
    // GET SUBORDERS BELONGING TO A SPECIFIC ORDER
    @GetMapping(path="/getSubOrdersByOrder")
    public List<SubOrder> getSubOrdersByOrder (
        @RequestParam String orderID
    ){
        Order order = orderRepository.findById(Integer.parseInt(orderID)).get(); // .get() is VERY important here as it will return the actual object and not just a reference
        return subOrderRepository.findByOrder(order);
    }
    
    
    /** MARK A SUBORDER AS READY FOR COLLECTION
     * @param subOrderId
     * @return String - status of suborder
     */
    @PostMapping(path="/markSubOrderAsReady")
    public String markSubOrderAsReady(
        @RequestParam String subOrderId
    ){
        SubOrder subOrder = subOrderRepository.findById(Integer.parseInt(subOrderId)).get();
        int i = OrderStatus.READY.ordinal();  // set the status as READY, see the "Enums" class
        subOrder.setOrderStatus(i);
        subOrderRepository.save(subOrder);
        
        // CHECK IF ALL SUBORDERS OR ORDER ARE READY //
        // first we get the parent Order
        Order order = subOrder.getOrder();
        
        // get a list of all the sub orders
        List<SubOrder> subOrders = subOrderRepository.findAll();
        
        
        // compile a list of all the SubOrders that point to our parent order
        List<SubOrder> relevantSubOrders = new ArrayList<>(); // we will store each one in this list
        for (SubOrder s : subOrders) {
            if (s.getOrder() == order) {
                relevantSubOrders.add(s);
            }
        }
        
        
        // Check the status of each SubOrder
        boolean failed = false;
        for (SubOrder s : relevantSubOrders) {
            if (s.getOrderStatus() != OrderStatus.READY.ordinal()) {
                failed = true;
                System.out.println("Some items remain unfulfilled.");
                break; // exit the loop early as we know the Order isn't complete yet
            }
        }
        
        
        // if all the SubOrders have been marked as ready, we mark
        // the parent Order as ready for collection. Drivers need
        // to update their request to see the new delivery.
        if (!failed) {
            System.out.println("****** ALL PARTS OF ORDER ARE READY! ******* ");
            System.out.println("Driver " + order.getDriver().getFirstName() + " " + order.getDriver().getSurname() + " will be notified.");
            order.setOrderStatus(OrderStatus.READY.ordinal());
            orderRepository.save(order);
        }
        
        return "\n\n\nSubOrder: " + subOrderId + " status is now " + subOrder.getOrderStatus() +"\n\n\n";
    }
}