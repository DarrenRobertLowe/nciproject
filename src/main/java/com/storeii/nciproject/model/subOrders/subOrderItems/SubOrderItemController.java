
package com.storeii.nciproject.model.subOrders.subOrderItems;

/**
 *
 * @author Darren Robert Lowe
 */

import com.storeii.nciproject.model.products.ProductRepository;
import com.storeii.nciproject.model.subOrders.SubOrderRepository;
import com.storeii.nciproject.model.subOrders.SubOrder;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController // This means that this class is a Controller and @Rest means we don't need to include @ResponseBody
@RequestMapping(path="/webstoredb") // This means URL's start with /webstoredb (after Application path)
public class SubOrderItemController {
    @Autowired
    private SubOrderItemRepository subOrderItemRepository;
    
    @Autowired
    private SubOrderRepository subOrderRepository;
    
    @Autowired
    private ProductRepository productRepository;
    
    
    
    // Add new
    // Note: try catch wrapping here will break the server
    @PostMapping(path="/addSubOrderItem") // Map ONLY POST Requests
    public String addSubOrderItem (
        @RequestParam String subOrderId,
        @RequestParam String productId,
        @RequestParam String quantity
    )
    {
      SubOrderItem subOrderItem = new SubOrderItem();
      
      //quantity
      subOrderItem.setQuantity(Integer.parseInt(quantity));
      
      // Foreign Keys
      int i = Integer.parseInt(subOrderId);
      SubOrder parentSubOrder = subOrderRepository.getById(i);
      
      subOrderItem.setSubOrder(parentSubOrder);
      
      // this is so we can get a list of SubOrderItems from a given SubOrder.
      //parentSubOrder.addItem(subOrderItem); 
      
      int p = Integer.parseInt(productId);
      subOrderItem.setProduct(productRepository.getById(p));
      
      
      subOrderItemRepository.save(subOrderItem);
      return "Saved";
    }
    
    
    // find all
    @GetMapping(path="/getSubOrderItems")
    public Iterable<SubOrderItem> getSubOrderItems() {
      return subOrderItemRepository.findAll();  // This returns a JSON or XML with the users
    }
    
    
    
    // GET ITEMS BELONGING TO A SPECIFIC SUBORDER
    @GetMapping(path="/getItemsFromSubOrder")
    //@Query("select * from SubOrderItems where subOrderID = ?")
    public List<SubOrderItem> getItemsFromSubOrder (
        @RequestParam String subOrder
    ){
        // NOTE: we can probably simplify the below line by instead using EntityManager.
        SubOrder realOrder = subOrderRepository.findById(Integer.parseInt(subOrder)).get(); // .get() is VERY important here as it will return the actaul object and not just a reference
        return subOrderItemRepository.findBySubOrder(realOrder);
    }
    
    
    
    /*
    // GET SUBORDER BELONGING TO A SPECIFIC SUPPLIER THAT NEED TO BE FULFILLED
    @GetMapping(path="/getFulfilmentsBySupplier")
    public List<Order> getFulfilmentsBySupplier (
        @RequestParam String supplierID
    ){
        Supplier supplier = supplierRepository.findById(Integer.parseInt(supplierID)).get(); // .get() is VERY important here as it will return the actual object and not just a reference
        return orderRepository.findOrdersByDriverAndOrderStatus(driver, 2);
    }
    */
}