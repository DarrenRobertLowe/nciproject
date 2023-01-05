package com.storeii.nciproject.model.CartItem;

import com.storeii.nciproject.User;
import com.storeii.nciproject.UserService;
import com.storeii.nciproject.model.Customer.Customer;
import com.storeii.nciproject.model.Customer.CustomerRepository;
import com.storeii.nciproject.model.products.Product;
import com.storeii.nciproject.model.products.ProductRepository;
import com.storeii.nciproject.model.website.CartService;
import com.storeii.nciproject.model.website.WebsiteController;
import javax.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Darren Robert Lowe
 */

@RestController // This means that this class is a Controller and @Rest means we don't need to include @ResponseBody
//@RequestMapping(path="/webstoredb") // This means URL's start with /webstoredb (after Application path)
public class CartItemController {
    @Autowired
    private CartItemRepository cartItemRepository;
    
    @Autowired
    private CustomerRepository cutomerRepository;
    
    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
    // this allows us to refer to the objects easily
    private EntityManager entityManager;
    
    @Autowired
    private CartService cartService;
    
    @Autowired
    private WebsiteController webController;
    
    @Autowired
    private UserService userService;
    
    // Add new
    @GetMapping(path="/addCartItem")
    public ModelAndView addCartItem (
        @RequestParam int customerID,
        @RequestParam int productID,
        @RequestParam int quantity
    ) {
        // check the user is logged in.
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal == "anonymousUser") {
            System.out.println("USER IS NOT LOGGED IN!");
        } else {
            // get the entities
            Customer customer = entityManager.find(Customer.class, customerID);
            Product product   = entityManager.find(Product.class, productID);
            
            // make sure customer is allowed to add to this cart
            User user = userService.getUser();
            int userCustomer = user.getCustomer().getId();
            if (userCustomer != customerID) {
                System.out.println("Customer did not match user! Access denied!");
                String redirectURL = "/login";
                return new ModelAndView(redirectURL);
            }
            
            // declare a CartItem
            CartItem cartItem;
            
            // before we create a new cartItem, check if the product is already in the cart
            // this way we can avoid duplicate products in the cart and set the quantity correctly
            cartItem = cartService.checkForProductInCart(customer, product);    // this returns the CartItem if the product already exists
            if (cartItem == null) {
                cartItem = new CartItem();      // if it doesn't already exist, create a new cartItem.
                cartItem.setQuantity(quantity); // we can just set the quantity to the provided amount
            } else {
                int existingQty = cartItem.getQuantity();       // get the existing quantity
                cartItem.setQuantity(quantity + existingQty);   // add the given quantity to the existing quantity
            }
            
            
            // set the other fields
            cartItem.setCustomer(customer);
            cartItem.setProduct(product);
            
            
            // save the repo
            cartItemRepository.save(cartItem);
            
            String redirectURL = "redirect:/cart?customerID=" + customerID;
            return new ModelAndView(redirectURL);
        }
        
        // in the event of a problem
        String redirectURL = "/login";
        return new ModelAndView(redirectURL);
    }
    
    
    
    // find all
    @GetMapping(path="/getCartItems")
    public Iterable<CartItem> getCartItems() {
      return cartItemRepository.findAll();  // This returns a JSON or XML with the users
    }
    
    
    
    @GetMapping(path="/getCartItemsByCustomer")
    public Iterable<CartItem> getCartItemsByCustomer(
        @RequestParam String customerID
    ){
        Customer customer = entityManager.find(Customer.class, Integer.parseInt(customerID));
        
        return cartItemRepository.findByCustomer(customer);
    }
}