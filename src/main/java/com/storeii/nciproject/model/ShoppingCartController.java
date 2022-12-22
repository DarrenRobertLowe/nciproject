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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import org.springframework.web.servlet.view.RedirectView;

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
    private ProductRepository productRepository;
    
    
    
    @Autowired
    private WebsiteController webController;
    
    
    
    
    @GetMapping("/cart")
    public ModelAndView showShoppingCart() {
        // Create a Model And View
        ModelAndView mav = new ModelAndView("shopping-cart");
        String status = "ANONYMOUS";
        int customerID = -1;
        boolean somethingOverStock = false; // lets the page know a cart item's quantity exceeds the available stock
        List<CartItem> cartItems = new ArrayList();
        List<Double> cartItemTotals = new ArrayList(); // price * quantity for each item
        double totalPrice = 0;
        double deliveryCost = 6.0;
        
        // get the user role for the navbar
        String userRole = webController.getUserRole();
        webController.getNavbar(mav);
        mav.addObject("userType", userRole);
        
        
        // we'll need the User to check if the id corresponds to the cart
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        
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
                        
                        if (item.getQuantity() > item.getProduct().getStock() ){
                            somethingOverStock = true;
                        }
                        
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
        
        mav.addObject("somethingOverStock", somethingOverStock);
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
        // get the cartItem
        CartItem cartItem = entityManager.find(CartItem.class, Integer.parseInt(cartItemId));
        
        if (verifyCartItem(cartItem)) {
            System.out.println("Reducing quantity...");


            int qty = cartItem.getQuantity();

            if (qty > 1) {
                cartItem.setQuantity(qty-1);
                cartItemRepository.save(cartItem);
            }
        }
    }
    
    

    @PostMapping("/cart/quantityUp")
    public void quantityUp(String cartItemId){
        // get the cartItem
        CartItem cartItem = entityManager.find(CartItem.class, Integer.parseInt(cartItemId));

        if (verifyCartItem(cartItem)) {
            System.out.println("Increasing quantity...");

            int qty = cartItem.getQuantity();
            cartItem.setQuantity(qty+1);
            cartItemRepository.save(cartItem);
        }
    }
    
    
    @PostMapping("/cart/removeItem")
    public void removeCartItem(String cartItemId){
        // get the cartItem
        CartItem cartItem = entityManager.find(CartItem.class, Integer.parseInt(cartItemId));
        
        if (verifyCartItem(cartItem)) {
            System.out.println("Deleting item from cart...");
            cartItemRepository.deleteById(cartItem.getId());
        }
    }
   
    
        
        
    // VERIFY CARTITEM
    // Verifies a CartItem belongs to the logged in user.
    public boolean verifyCartItem(CartItem cartItem){    
        // we'll need the User to check if the id corresponds to the cart
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        
        if (principal == "anonymousUser") {
            System.out.println("USER IS NOT LOGGED IN!");
            return false;
        } else {
            User user = webController.getUser();
            int userCustomerId = user.getCustomer().getId();
            
            // get the cartItem
            if (userCustomerId != cartItem.getCustomer().getId()) {
                System.out.println("CartItem did not match user customer id! Access denied!");
                return false;
            } else {
                return true;
            }
        }
    }
        
    
    
    
    @GetMapping("/quantity-fragment")
    public ModelAndView getQuantity(int cartItemId) {
        CartItem cartItem = entityManager.find(CartItem.class, cartItemId);
        
        ModelAndView mv = new ModelAndView("quantity-fragment");
        mv.addObject("item",cartItem);
        return mv;
    }
    
    
    
    @Transactional
    @PostMapping(path="/checkout")
    public ResponseEntity checkout(String customerId){
        // we'll need the User to check if the id corresponds to the cart
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        
        if (principal == "anonymousUser") {
            System.out.println("USER IS NOT LOGGED IN!");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            User user = webController.getUser();
            int userCustomer = user.getCustomer().getId();
            if (userCustomer != Integer.parseInt(customerId)) {
                System.out.println("Customer did not match user! Access denied!");
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            
            ModelAndView mav = new ModelAndView();
            String message = "";
            
            // get the entity
            Customer customer = entityManager.find(Customer.class, Integer.parseInt(customerId));

            List<CartItem> cartItems = cartItemRepository.findByCustomer(customer);


            // Create a new Order object, the details are added later with addOrder();
            Order order = new Order();
            orderRepository.save(order);


            // create a list for the orderItems
            List<OrderItem> oList = new ArrayList<>();

            // Create an OrderItem for each CartItem
            for(CartItem cItem : cartItems){
                int quantity     = cItem.getQuantity();
                double unitPrice = cItem.getProduct().getPrice();
                Product product  = cItem.getProduct();


                OrderItem oItem  = new OrderItem(order, product, quantity, unitPrice);

                // Update the product's stock
                int stock = product.getStock();

                if (stock < quantity) {
                    System.out.println("STOCK < QUANTITY!");
                    message = "Oops! Looks like someone got there before you! The quantity of your order exceeds the number of items in stock. Please try again with a smaller quantity.";
                    mav.addObject("message", message);
                    //return mav;//
                    //orderproblem(mav);
                    return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
                } else {
                    System.out.println("stock is fine.");
                    product.setStock(stock - quantity);
                    productRepository.save(product);

                    // save the item
                    orderItemRepository.save(oItem);
                    oList.add(oItem); // add the orderItem to the list
                }
            }

            System.out.println("_____orderItems_____\n"+ oList.toString());


            // Run the addOrder() method
            OrderController controller = new OrderController();
            controller.addOrder(order, customer, oList, subOrderItemRepository, orderRepo);

            // EMPTY THE CART CONTENTS
            cartItemRepository.deleteAllByCustomer(customer);

            // REDIRECT
            mav.addObject("message", message);
            //return mav;//
            return new ResponseEntity<>(HttpStatus.OK);
            //return "/checkout";
        }
    }
    
    
    
    
    @GetMapping(path = "/outOfStock") 
    public ModelAndView outOfStock(String message) {
        System.out.println("Running /outOfStock");
        ModelAndView mav = new ModelAndView();
        mav.addObject("message", message);
        //orderproblem(mav);
        return mav;
    }
    
    
    
    public RedirectView orderproblem(ModelAndView model) {
        System.out.println("Running /orderproblem");
        //return model;
        //model.addAttribute(message);
        return new RedirectView("/orderproblem");
    }
}
