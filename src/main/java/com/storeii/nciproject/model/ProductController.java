/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.storeii.nciproject.model;

/**
 *
 * @author Main
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController // This means that this class is a Controller and @Rest means we don't need to include @ResponseBody
@RequestMapping(path="/webstoredb") // This means URL's start with /webstoredb (after Application path)
public class ProductController {
    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
    private SupplierRepository supplierRepository;
    
    // Add new
    @PostMapping(path="/addProduct")
    public String addProduct(
        @RequestParam String productName,
        @RequestParam String productDescription,
        @RequestParam String image,
        @RequestParam String price,
        @RequestParam String stock,
        @RequestParam String category,
        @RequestParam String supplier_ID
        )
    {
        Product product = new Product();
        
        product.setProductName(productName);
        product.setProductDescription(productDescription);
        product.setImage(image);
        product.setCategory(category);
        product.setPrice(Double.parseDouble(price));
        product.setStock(Integer.parseInt(stock));
        
        // foreign keys
        int i = Integer.parseInt(supplier_ID);
        product.setSupplier(supplierRepository.getById(i));
        
        productRepository.save(product);
        
        return "Saved to index " + product.getId();
    }
    
    
    // find all
    @GetMapping(path="/getProducts")
    public Iterable<Product> getProducts() {
      return productRepository.findAll();  // This returns a JSON or XML with the users
    }
    
}