package com.storeii.nciproject.model.orders;
import com.storeii.nciproject.model.deliveries.Driver;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Darren Robert Lowe
 */

// Spring automatically implements this repository interface in 
// a bean of the same name (lowecase first letter)
public interface OrderRepository extends
        JpaRepository<Order, Integer>
{
    Order getById(int id);
    public List<Order> findByDriver(Driver driver);

    
    public List<Order> findOrdersByDriverAndOrderStatus(Driver driver, int orderStatus);

}