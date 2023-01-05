
package com.storeii.nciproject.model.fulfilments;

import javax.persistence.Embeddable;
import java.io.Serializable;
import javax.persistence.Column;
/**
 *
 * @author Darren Robert Lowe
 */
@Embeddable
public class SupplierOrders implements Serializable {
    @Column(name = "supplier_id")
    Long supplierId;

    @Column(name = "order_id")
    Long orderId;
}
