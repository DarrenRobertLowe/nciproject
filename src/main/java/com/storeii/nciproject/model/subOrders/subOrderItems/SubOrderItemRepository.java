
package com.storeii.nciproject.model.subOrders.subOrderItems;


import com.storeii.nciproject.model.subOrders.SubOrder;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Darren Robert Lowe
 */

// Spring automatically implements this repository interface in 
// a bean of the same name (lowecase first letter)
public interface SubOrderItemRepository extends CrudRepository<SubOrderItem, Integer> {
    SubOrderItem getById(int id);
    
    
    public List<SubOrderItem> findBySubOrder(SubOrder subOrder);
}