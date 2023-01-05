package com.storeii.nciproject.model.website;

import com.storeii.nciproject.model.CartItem.CartItem;
import com.storeii.nciproject.model.CartItem.CartItemRepository;
import com.storeii.nciproject.model.Customer.Customer;
import com.storeii.nciproject.model.products.Product;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Darren Robert Lowe
 */
@Service
public class CartService {
    
    @Autowired
    CartItemRepository cartItemRepository;
    
    /**
     * Checks to see if a given item already exists in a customer's cart.
     * The customer will be found by the Customer object, and the product
     * will be checked against using the product id.
     * 
     * @return CartItem
     * @param customer the customer to check against
     * @param product the product to check for
    */
    public CartItem checkForProductInCart(Customer customer, Product product) {
        // get the customer's cart items
        List<CartItem> cartItemList = cartItemRepository.findByCustomer(customer);
        
        // check for the given product
        for(CartItem item: cartItemList) {
            if (item.getProduct().getId() == product.getId()) {
               return item;
            }
        }
        
        return null;
    }
}
