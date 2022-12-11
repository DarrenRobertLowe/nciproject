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
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
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
    
    @Autowired
    private WebsiteController webController;
    
    @GetMapping("/cart")
    public ModelAndView showShoppingCart() {
        // Create a Model And View
        ModelAndView mav = new ModelAndView("shopping-cart");
        String status = "ANONYMOUS";
        int customerID = -1;
        List<CartItem> cartItems = new ArrayList();
        List<Double> cartItemTotals = new ArrayList(); // price * quantity for each item
        double totalPrice = 0;
        double deliveryCost = 6.0;
        
        // get the user role for the navbar
        String userRole = webController.getUserRole();
        webController.getNavbar(mav);
        mav.addObject("userType", userRole);
        
        
        // we'll need the User to check if the id corresponds to the cart
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("AUTH IS " + auth);
        
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println("Prinicpal is " + principal);
        
        
        if (principal == "anonymousUser") {
            System.out.println("USER IS NOT LOGGED IN!");
            status = "ANONYMOUS";
        } else {
            // we're logged in
            UserPrincipal userPrincipal = (UserPrincipal)principal; // make sure you're logged in or you'll get an error!
            
            
            if ((principal instanceof UserDetails) == false){
                // I'm not sure this can happen, but just in case
                status = "INVALID";
            } else {
                // get the User
                User user = userPrincipal.getUser();
                
                // Check if valid
                if (user.getCustomer() == null) {
                    // prevent Suppliers and Drivers placing orders
                    status = "INVALID";
                } else {
                    // get the Customer entity
                    customerID = user.getCustomer().getId();
                    Customer customer = entityManager.find(Customer.class, customerID);
                    cartItems = cartItemRepository.findByCustomer(customer);
                    
                    // get the totalPrice of the cart items
                    for (CartItem item : cartItems) {
                        double subTotal = ( item.getProduct().getPrice() * item.getQuantity() );
                        cartItemTotals.add(subTotal);
                        totalPrice += subTotal;
                    }
                    
                    // prevent customers accessing other carts
                    if ((user.getCustomer().getId()) != customerID) {
                        status = "INVALID";
                    } else {
                        System.out.println("****** ACCESS GRANTED ******");
                        status = "AUTHORIZED";
                    }
                }
            }
        }
        
        
        mav.addObject("customerId", customerID);
        mav.addObject("cartItems", cartItems);
        mav.addObject("cartItemTotals", cartItemTotals);
        mav.addObject("totalPrice", totalPrice);
        mav.addObject("deliveryCost", deliveryCost);
        mav.addObject("status", status);
        mav.addObject("image_directory","../assets/img/products/");
        return mav;
    }
    
    
    
    
    
    
    
    
    @PostMapping("/cart/quantityDown")
    public void quantityDown(String cartItemId){
        System.out.println("Reducing quantity...");
        // get the entity
        CartItem cartItem = entityManager.find(CartItem.class, Integer.parseInt(cartItemId));
        
        int qty = cartItem.getQuantity();
        
        if (qty > 1) {
            cartItem.setQuantity(qty-1);
            cartItemRepository.save(cartItem);
        }
    }
    
    

    @PostMapping("/cart/quantityUp")
    public void quantityUp(String cartItemId){
        System.out.println("Increasing quantity...");
        
        // get the entity
        CartItem cartItem = entityManager.find(CartItem.class, Integer.parseInt(cartItemId));
        
        int qty = cartItem.getQuantity();
        cartItem.setQuantity(qty+1);
        cartItemRepository.save(cartItem);
    }
    
    
    @PostMapping("/cart/removeItem")
    public void removeCartItem(String cartItemId){
        System.out.println("Deleting item from cart...");
        
        // get the entity
        CartItem cartItem = entityManager.find(CartItem.class, Integer.parseInt(cartItemId));
        
        cartItemRepository.deleteById(cartItem.getId());
        //cartItemRepository.save();
        
        //System.out.println("The deleted cartItem was : " + cartItemId);
    }
    
    
    
    
    //@CrossOrigin(origins = "http://localhost:8080")
    @GetMapping("/quantity-fragment")
    public ModelAndView getQuantity(int cartItemId) {
        CartItem cartItem = entityManager.find(CartItem.class, cartItemId);
        
        ModelAndView mv = new ModelAndView("quantity-fragment");
        mv.addObject("item",cartItem);
        return mv;
    }
    
    
    
    @Transactional
    @PostMapping(path="/checkout")
    public String checkout(String customerId){
        // get the entity
        Customer customer = entityManager.find(Customer.class, Integer.parseInt(customerId));
        
        List<CartItem> cartItems = cartItemRepository.findByCustomer(customer);
        
        
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
        
        // EMPTY THE CART CONTENTS
        cartItemRepository.deleteAllByCustomer(customer);
        
        // REDIRECT
        String redirectURL = "redirect:/orderPlaced";
        //return new ModelAndView(redirectURL);
        return "Order placed";
    }
    
}
