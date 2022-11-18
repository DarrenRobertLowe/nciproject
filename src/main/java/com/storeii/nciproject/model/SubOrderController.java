/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.storeii.nciproject.model;

/**
 *
 * @author Main
 */


import com.storeii.nciproject.Enums.OrderStatus;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
    
    
    // MARK A SUBORDER AS READY FOR COLLECTION
    @PostMapping(path="/markSubOrderAsReady")
    public String markSubOrderAsReady(
        @RequestParam String subOrderId
    ){
        SubOrder subOrder = subOrderRepository.findById(Integer.parseInt(subOrderId)).get();
        
        int i = OrderStatus.READY.ordinal();  // set the status as READY, see the "Enums" class
        
        subOrder.setOrderStatus(i);
        
        subOrderRepository.save(subOrder);
        
        
        /// We now need to check if all the SubOrders in Order are complete.
        
        // NOTE We should be able to simply run Set orders = order.getSubOrders();
        // but attempting to add that functionality caused things to break.
        // For now we're doing it in a unidirectional way...
        
        // first we get the parent Order
        Order order = subOrder.getOrder();
        //Set orders = order.getSubOrders();    // uncomment when we have bidirectionality working
        
        
        /// Uni-Directional
        // get a list of all the sub orders
        SubOrderController subOrderController = new SubOrderController();
        //Iterable<SubOrder> subOrders = subOrderController.getSubOrders();   
        Iterable<SubOrder> subOrders = subOrderRepository.findAll();
        
        
        // compile a list of all the SubOrders that point to our parent order
        List<SubOrder> relevantSubOrders = new ArrayList<>();
        for (SubOrder s : subOrders) {
            if (s.getOrder() == order) {
                System.out.println("***** SUBORDER POINTS TO ORDER *****");
                relevantSubOrders.add(s);
            }
        }
        
        // Check the status of each SubOrder
        boolean failed = false;
        for (SubOrder s : relevantSubOrders) {
            if (s.getOrderStatus() != OrderStatus.READY.ordinal()) {
                failed = true;
                break;          // exit the loop early as we know the Order isn't complete yet
            }
        }
        
        // if all the SubOrders have been marked as ready, we mark
        // the parent Order as ready for collection. Drivers need
        // to update their request to see the new delivery.
        if (!failed) {
            System.out.println("****** SUCCESS!! ALL PARTS OF ORDER ARE READY! ******* ");
            order.setOrderStatus(OrderStatus.READY.ordinal());
            orderRepository.save(order);
        }
        
        return "\n\n\nSubOrder: " + subOrderId + " status is now " + subOrder.getOrderStatus() +"\n\n\n";
    }
}