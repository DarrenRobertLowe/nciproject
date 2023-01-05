package com.storeii.nciproject.model.orders.orderItems;

import com.storeii.nciproject.model.orders.Order;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.storeii.nciproject.model.products.Product;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author Darren Robert Lowe
 */
@Entity // This tells Hibernate to make a table out of this class
@Table(name="OrderItems")
public class OrderItem {
    /*
    id int UNSIGNED auto_increment PRIMARY KEY,
    orderId int UNSIGNED NOT NULL,
    productId int UNSIGNED NOT NULL,
    quantity tinyint UNSIGNED NOT NULL,	-- 255 is probably enough for any 1 item in an order
    unitPrice decimal NOT NULL
    */
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int    id;
    private int    quantity;
    private double unitPrice;
    
    
    
     // FOREIGN KEYS
    @ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinColumn(name = "order_ID", referencedColumnName = "id")
    @JsonBackReference
    private Order order;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_ID", referencedColumnName = "id")
    private Product product;
    
    
    
    // GETTERS and SETTERS
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public int getQuantity() {
        return quantity;
    }
    
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    public Order getOrder() {
        return order;
    }
    
    public void setOrder(Order orderId) {
        this.order = orderId;
    }
    
    public Product getProduct() {
        return product;
    }
    
    public void setProduct(Product product) {
        this.product = product;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }
    
    
    /// CONSTRUCTORS
    public OrderItem(){
    }
    
    public OrderItem(Order orderID, Product productID, int quantity, double unitPrice) {
        this.order      = orderID;
        this.product    = productID;
        this.quantity   = quantity;
        this.unitPrice  = unitPrice;
    }
}
