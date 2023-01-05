
package com.storeii.nciproject.integrationTesting;

import com.storeii.nciproject.User;
import com.storeii.nciproject.UserRepository;
import com.storeii.nciproject.model.CartItem.CartItemController;
import com.storeii.nciproject.model.Customer.Customer;
import com.storeii.nciproject.model.website.ShoppingCartController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class IntegrationTests {
    /*
        @Autowired
        UserRepository userRepository;
        
        @Autowired
        ShoppingCartController shoppingCartController;
        
        @Autowired
        CartItemController cartItemController;
        
        
	@Test
        @DisplayName("Supplier should receive the new order")
	void checkout() {
            User user = userRepository.getByUserName("testuser");
            Customer customer = user.getCustomer();
            int userId = customer.getId();
            String userString = Integer.toString(userId);
            int productId;
            int productQty;
            
            // remove all cart items
            shoppingCartController.emptyCart(customer.getId());
            
            // add cart items
            productId  = 27; // 27 = Sprint Sportswear Cloud Runners Gray from Fresh Kicks
            productQty = 3;
            cartItemController.addCartItem(userId, productId, productQty);
            
            productId  = 31; // 31 = Nike Green Envy's from Winstons
            productQty = 2;
            cartItemController.addCartItem(userId, productId, productQty);
            
            // checkout
            shoppingCartController.checkout(userString);
	}
    */
}
