/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/javascript.js to edit this template
 */



function confirmMarkAsReady(subOrderId) {
  if (confirm("Are you sure you want to confirm order id: " + subOrderId + " and remove it from your queue?")) {
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
        window.location.reload();   // Note this is supposedly a bad way of doing things. Instead we should be updating using the DOM.
    } else {
    }
}




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
        window.location.reload();   // Note this is supposedly a bad way of doing things. Instead we should be updating using the DOM.
    } else {
    }
}