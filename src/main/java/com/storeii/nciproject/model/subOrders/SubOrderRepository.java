package com.storeii.nciproject.model.subOrders;

import com.storeii.nciproject.model.fulfilments.Supplier;
import com.storeii.nciproject.model.orders.Order;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 *
 * @author Darren Robert Lowe
 */

// Spring automatically implements this repository interface in 
// a bean of the same name (lowecase first letter)
public interface SubOrderRepository extends JpaRepository<SubOrder, Integer> {
    SubOrder getById(int id);
    
    public List<SubOrder> findBySupplier(Supplier supplier);
    
    public List<SubOrder> findByOrder(Order order);
    
    public List<SubOrder> findSubOrdersBySupplierAndOrderStatus(Supplier supplier, int orderStatus);
}