package com.storeii.nciproject.model.products;

import com.storeii.nciproject.model.fulfilments.Supplier;
import com.fasterxml.jackson.annotation.JsonBackReference;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 *
 * @author Darren Robert Lowe
 */
@Entity // This tells Hibernate to make a table out of this class
public class Product implements Comparable<Product> {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int     id;
    private String  productName;
    private String  productDescription;
    private String  image;
    private double  price;
    private int     stock;
    private String  category;
    private String  identifier;
    
    
    // FOREIGN KEYS
    @ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinColumn(name = "supplier_ID", referencedColumnName = "id")
    @JsonBackReference
    private Supplier supplier;
    
    
    // GETTERS and SETTERS
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    
    
    
    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }
    
    
    /// Comparator
    @Override
    public int compareTo(Product otherProduct){
        return (productName.compareTo(otherProduct.getProductName()));
    }
}
