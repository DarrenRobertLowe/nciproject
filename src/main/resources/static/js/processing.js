/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/javascript.js to edit this template
 */


/// MARK AS READY FOR PICKUP ///
function confirmMarkAsReady(subOrderId, orderId) {
  if (confirm("Are you sure you want to confirm order id: " + orderId + " and remove it from your queue?")) {
      markAsReady(subOrderId);
  }
}

async function markAsReady(subOrderId) {
    const options = {
        method: 'POST'
    };
    
    const response = await fetch( '/webstoredb/markSubOrderAsReady?subOrderId='+subOrderId, options);
    
    if (response.status === 200) { // success
        // refresh page to refresh the list
        window.location.reload();
    } else {
    }
}




/// MARK AS DELIVERED ///
function confirmMarkAsDelivered(orderID) {
  if (confirm("Are you sure you want to confirm delivery for order id: " + orderID + " and remove it from your queue?")) {
      markAsDelivered(orderID);
  }
}

async function markAsDelivered(orderID) {
    const options = {
        method: 'POST'
    };
    
    const response = await fetch( '/webstoredb/markOrderAsDelivered?orderID='+orderID, options);
    
    if (response.status === 200) { // success
        // refresh page to refresh the list
        window.location.reload();
    } else {
    }
}

/// MARK AS RETURNED/CANCELLED ///
function confirmReturnedByCustomer(orderId) {
  if (confirm("Are you sure you want to return order id: " + orderId + " and remove it from your queue?")) {
      markAsReturned(orderId);
  }
}

async function markAsReturned(orderID) {
    const options = {
        method: 'POST'
    };
    
    const response = await fetch( '/webstoredb/markOrderAsReturned?orderID='+orderID, options);
    
    if (response.status === 200) { // success
        // refresh page to refresh the list
        window.location.reload();
    } else {
    }
}