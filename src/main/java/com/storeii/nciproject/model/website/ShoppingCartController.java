package com.storeii.nciproject.model.website;

import com.storeii.nciproject.model.products.Product;
import com.storeii.nciproject.model.products.ProductRepository;
import com.storeii.nciproject.model.orders.OrderRepository;
import com.storeii.nciproject.model.orders.orderItems.OrderItem;
import com.storeii.nciproject.model.orders.orderItems.OrderItemRepository;
import com.storeii.nciproject.model.orders.Order;
import com.storeii.nciproject.model.orders.OrderController;
import com.storeii.nciproject.model.Customer.Customer;
import com.storeii.nciproject.model.CartItem.CartItemRepository;
import com.storeii.nciproject.model.CartItem.CartItem;
import com.storeii.nciproject.User;
import com.storeii.nciproject.UserService;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

/**
 *
 * @author Darren Robert Lowe
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
    private ProductRepository productRepository;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private WebsiteController webController;
    
    @Autowired
    private WebsiteResourcesService resourcesService;
    
    @Autowired
    private OrderController orderController;
    
    
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
        double deliveryCost = resourcesService.getDeliveryCost();
        
        
        // get the user role for the navbar
        String userRole = userService.getUserRole();
        webController.getNavbar(mav);
        
        System.out.println("userRole is : " + userRole);
        
        
        if (userRole.equalsIgnoreCase("CUSTOMER")) {
            System.out.println("****** ACCESS GRANTED ******");
            status = "AUTHORIZED";
            
            
            // get the Customer entity
            User user = userService.getUser();
            customerID = user.getCustomer().getId();
            Customer customer = entityManager.find(Customer.class, customerID);
            
            // get the cart items
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
        } else {
            System.out.println("USER IS NOT LOGGED IN!");
        }
            
        
        
        mav.addObject("somethingOverStock", somethingOverStock);
        mav.addObject("customerId", customerID);
        mav.addObject("cartItems", cartItems);
        mav.addObject("cartItemTotals", cartItemTotals);
        mav.addObject("totalPrice", totalPrice);
        mav.addObject("deliveryCost", deliveryCost);
        mav.addObject("status", status);
        mav.addObject("image_directory", resourcesService.getImageDirectory() );
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
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        
        if (principal == "anonymousUser") {
            System.out.println("USER IS NOT LOGGED IN!");
            return false;
        } else {
            User user = userService.getUser();
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
            User user = userService.getUser();
            int userCustomer = user.getCustomer().getId();
            if (userCustomer != Integer.parseInt(customerId)) {
                System.out.println("Customer did not match user! Access denied!");
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            // Setup 
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

                    return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
                } else {
                    // stock is fine
                    product.setStock(stock - quantity);
                    productRepository.save(product);

                    // save the item
                    orderItemRepository.save(oItem);
                    oList.add(oItem); // add the orderItem to the list
                }
            }

            // Run the addOrder() method
            orderController.addOrder(order, customer, oList);
            
            // EMPTY THE CART CONTENTS
            cartItemRepository.deleteAllByCustomer(customer);
            
            // REDIRECT
            mav.addObject("message", message);
            return new ResponseEntity<>(HttpStatus.OK);
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
    
    
    
    // REMOVE ALL FROM CART
    @Transactional
    @GetMapping("/cart/emptyCart")
    public void emptyCart(int customerId) {
        boolean valid = true;       // does this cart belong to us?
        Customer customer = null;
        
        // we'll need the User to check if the id corresponds to the cart
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        
        // verify the cartItems
        if (principal == "anonymousUser") {
            System.out.println("CANNOT EMPTY CART - USER IS NOT LOGGED IN!");
            valid = false;
        } else {
            User user = userService.getUser();
            int userCustomerId = user.getCustomer().getId();
            customer = entityManager.find(Customer.class, customerId);
                    
            // find the cartItems
            if (customer != null) {
                List<CartItem> cartItems = cartItemRepository.findByCustomer(customer);
                
                for(CartItem item : cartItems) {
                    // get the cartItem
                    if (userCustomerId != item.getCustomer().getId()) {
                        System.out.println("CANNOT EMPTY CART - CartItem did not match user customer id! Access denied!");
                        valid = false;
                    }
                }
            } else {
                System.out.println("CANNOT EMPTY CART - USER IS NOT A CUSTOMER!");
                valid = false;
            }
        }
        
        // if this cart belongs to this customer
        if (valid == true && customer != null) {
            System.out.println("Empyting cart belonging to customer "+ customer.getId() +" cart...");
            cartItemRepository.deleteAllByCustomer(customer);
        }
    }
}
