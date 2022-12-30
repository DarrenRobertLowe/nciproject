/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.storeii.nciproject.model.CartItem;

import com.storeii.nciproject.User;
import com.storeii.nciproject.model.Customer.Customer;
import com.storeii.nciproject.model.Customer.CustomerRepository;
import com.storeii.nciproject.model.products.Product;
import com.storeii.nciproject.model.products.ProductRepository;
import com.storeii.nciproject.model.website.WebsiteController;
import javax.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

/**
 *
 * @author Main
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
    private WebsiteController webController;
    
    
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
            CartItem cartItem = new CartItem();

            // get the entities
            Customer customer = entityManager.find(Customer.class, customerID);
            
            User user = webController.getUser();
            int userCustomer = user.getCustomer().getId();
            if (userCustomer != customerID) {
                System.out.println("Customer did not match user! Access denied!");
                String redirectURL = "/login";
                return new ModelAndView(redirectURL);
            }
            
            
            Product product   = entityManager.find(Product.class, productID);
            
            // set the fields
            cartItem.setCustomer(customer);
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);

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