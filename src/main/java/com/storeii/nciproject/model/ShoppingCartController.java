/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.storeii.nciproject.model;

import com.storeii.nciproject.User;
import com.storeii.nciproject.UserPrincipal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Main
 */


@RestController
public class ShoppingCartController {
    
    @Autowired
    private EntityManager entityManager;    // this allows us to refer to the entities mroe easily
    
    @Autowired
    private CartItemRepository cartItemRepository;
    
    @Autowired
    private OrderRepository orderRepository;
    
    @Autowired
    private OrderItemRepository orderItemRepository;
    
    @Autowired
    private SubOrderItemRepository subOrderItemRepository;
    
    @Autowired
    private OrderRepository orderRepo;
    
    
    
    @GetMapping("/cart")
    public ModelAndView showShoppingCart(int customerID) {
        // get the entity
        Customer customer = entityManager.find(Customer.class, customerID);
        
        List<CartItem> cartItems = cartItemRepository.findByCustomer(customer);   // listCartItems(customer);
        
        
        
        // now we have the customer object we can check if the User id corresponds.
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserPrincipal userPrincipal = (UserPrincipal)principal;
        
        //String username = "None";
        if (principal instanceof UserDetails) {
            User user = userPrincipal.getUser();
            System.out.println("The user id is : " + user);
            System.out.println("The user name is : " + user.getUserName());
            System.out.println("The user password is : " + user.getUserPass());
        } else {
            System.out.println("The user id is : " + userPrincipal.getUser());
        }
        
        //System.out.println("username: " + username);
                
        
        ModelAndView mav = new ModelAndView("shopping-cart");
        
        mav.addObject("customerId", customerID);
        mav.addObject("cartItems", cartItems);
        return mav;
    }
    
    
    

    @PostMapping("/cart/quantityDown")
    public void quantityDown(String cartItemId){
        System.out.println("************** Hi, Hello there! *************");
        // get the entity
        CartItem cartItem = entityManager.find(CartItem.class, Integer.parseInt(cartItemId));
        
        int qty = cartItem.getQuantity();
        cartItem.setQuantity(qty-1);
        
        cartItemRepository.save(cartItem);
        
        System.out.println("The id was : " + cartItemId);
        
        System.out.println("************** Hi, Hello there! *************");
    }
    
    

    @PostMapping("/cart/quantityUp")
    public void quantityUp(String cartItemId){
        System.out.println("************** Hi, Hello there! *************");
        
        // get the entity
        CartItem cartItem = entityManager.find(CartItem.class, Integer.parseInt(cartItemId));
        
        int qty = cartItem.getQuantity();
        cartItem.setQuantity(qty+1);
        cartItemRepository.save(cartItem);
        
        System.out.println("************** Hi, Hello there! *************");
    }
    
    
    
    //@CrossOrigin(origins = "http://localhost:8080")
    @GetMapping("/quantity-fragment")
    public ModelAndView getQuantity(int cartItemId) {
        System.out.println("YES, THIS IS RUNNING!");

        CartItem cartItem = entityManager.find(CartItem.class, cartItemId);
        
        ModelAndView mv = new ModelAndView("quantity-fragment");
        mv.addObject("item",cartItem);
        return mv;
    }
    
    
    
    
    @PostMapping(path="/checkout")
    public String checkout(String customerId){
        // get the entity
        Customer customer = entityManager.find(Customer.class, Integer.parseInt(customerId));
        
        System.out.println("entityManager found " + customerId);
        
        List<CartItem> cartItems = cartItemRepository.findByCustomer(customer);
        
        System.out.println("_____cartItems_____\n"+ cartItems.toString());
        
        
        // Create a new Order object, the details are added later with addOrder();
        Order order = new Order();
        orderRepository.save(order);
        
        
        
        // create a list fort the orderItems
        List<OrderItem> oList = new ArrayList<>();
        
        // Create an OrderItem for each CartItem
        for(CartItem cItem : cartItems){
            int quantity     = cItem.getQuantity();
            double unitPrice = cItem.getProduct().getPrice();
            Product product  = cItem.getProduct();
            
            
            OrderItem oItem  = new OrderItem(order, product, quantity, unitPrice);
            orderItemRepository.save(oItem);
            
            oList.add(oItem); // add the orderItem to the list
        }
        
        System.out.println("_____orderItems_____\n"+ oList.toString());
        
        
        // Run the addOrder() method
        OrderController controller = new OrderController();
        controller.addOrder(order, customer, oList, subOrderItemRepository, orderRepo);
        
        
        return "Checkout success";
    }
    
    
    
    
}
