package com.storeii.nciproject.model.website;

import com.storeii.nciproject.model.CartItem.CartItem;
import com.storeii.nciproject.model.CartItem.CartItemRepository;
import com.storeii.nciproject.model.Customer.Customer;
import com.storeii.nciproject.model.products.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/*
 * @author Darren Robert Lowe
 * 
 * This class tests if a specific product exists in a cart or not.
 *
*/
class CartServiceTest {
        
	@InjectMocks // creates an instance of CartService and effectively autowires the cartItemRepo
	private CartService cartService;
        
	@Mock // creates a temporary version of the cartItemRepo
	private CartItemRepository cartItemRepositoryMock;
        
	@BeforeEach // runs before each @Test
	void init_mocks() {
                // This tells Mockito to look for @Mock annotations in this class and initialize them.
		MockitoAnnotations.openMocks(this); 
	}
        
        // test an item is IN the cart
	@Test
	public void testFoundProductInCart() {
		List<CartItem> cartItems = new ArrayList<>();
                
                // add cartItems
		cartItems.add(createCartItem(15));
		cartItems.add(createCartItem(5));
		cartItems.add(createCartItem(1));
                
		when(cartItemRepositoryMock.findByCustomer(any(Customer.class))).thenReturn(cartItems); // mocks the behaviour for any Customer
                
		CartItem cartItem = cartService.checkForProductInCart(new Customer(), createCartItem(5).getProduct());
		assertEquals(5, cartItem.getProduct().getId());
	}
        
        // test an item is NOT in the cart
	@Test
	public void testNoProductInCart() {
		List<CartItem> cartItemList = new ArrayList<>();
		cartItemList.add(createCartItem(15));
		cartItemList.add(createCartItem(5));
		cartItemList.add(createCartItem(1));
                
		when(cartItemRepositoryMock.findByCustomer(any(Customer.class))).thenReturn(cartItemList);
                
		CartItem returnedItem = cartService.checkForProductInCart(new Customer(), createCartItem(25).getProduct());
		assertNull(returnedItem);
	}
        
        // we need to create a new cart item without affecting
        // the actual database. We also don't need all the fields
        // filled for this test, so the productId is enough.
	private CartItem createCartItem(int productId) {
		Product product = new Product();
		product.setId(productId);
		CartItem cartItem = new CartItem();
		cartItem.setProduct(product);
		return cartItem;
	}
        
}