
package com.storeii.nciproject.model.CartItem;

import com.storeii.nciproject.model.Customer.Customer;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Darren Robert Lowe
 */
public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
    public List<CartItem> findByCustomer(Customer customer);
    
    public Integer deleteAllByCustomer(Customer customer);
}
