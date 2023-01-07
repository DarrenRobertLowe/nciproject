
package com.storeii.nciproject;

/**
 *
 * @author Darren Robert Lowe
 */
public class Enums {
    public static enum OrderStatus {
        CANCELLED,  // the order was cancelled for some reason
        CONFIRMED,  // the order is being fulfilled by suppliers
        READY,      // the order is ready for collection by the delivery driver
        DELIVERING, // the order is being delivered to the customer's address
        COMPLETE    // the order is with the customer
    }
}
