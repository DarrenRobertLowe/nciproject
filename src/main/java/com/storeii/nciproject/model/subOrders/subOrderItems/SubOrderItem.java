package com.storeii.nciproject.model.subOrders.subOrderItems;

import com.storeii.nciproject.model.subOrders.SubOrder;
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
@Table(name="SubOrder_Items")
public class SubOrderItem {
    /*
    id int UNSIGNED auto_increment PRIMARY KEY,
    subOrder_ID int UNSIGNED,
    product_ID int UNSIGNED,
    quantity tinyint UNSIGNED,	-- 255 is probably enough for any 1 item in an order
    FOREIGN KEY (subOrder_ID) REFERENCES SubOrder(id),
    FOREIGN KEY (product_ID) REFERENCES Product(id)
    */
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int    id;
    private int     quantity;

    
    
    // FOREIGN KEYS
    @ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinColumn(name = "subOrder_ID", referencedColumnName = "id")
    @JsonBackReference
    private SubOrder subOrder;
    
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

    public SubOrder getSubOrder() {
        return subOrder;
    }

    public void setSubOrder(SubOrder subOrder) {
        this.subOrder = subOrder;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
    
    
    /// CONSTRUCTORS
    public SubOrderItem(){
    }
    
    public SubOrderItem(SubOrder subOrder, Product productID, int quantity) {
        this.subOrder   = subOrder;
        this.product    = productID;
        this.quantity   = quantity;
    }
}
