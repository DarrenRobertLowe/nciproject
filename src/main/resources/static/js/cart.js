/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/javascript.js to edit this template
 */





async function confirmCheckout(customerId) {
    const options = {
        method: 'POST'
    };
    
    let response = await fetch('/checkout?customerId='+customerId, options);
    let responseText = await response.text();
    
    //document.getElementById('result').innerHTML = responseText;
    //alert(responseText);
    
    
    if (response.status === 200) { // success
        window.location.replace("/orderplaced");
        //window.location.reload();   // Note this is supposedly a bad way of doing things. Instead we should be updating using the DOM.
        //alert("customerId: " + customerId + " : Order placed successfully!");
    } else {
        window.location.replace("/outOfStock?message=" + responseText);
    }
}




async function increaseQty(cartItemId) {
    const options = {
        method: 'POST'
    };
    
    const response = await fetch( '/cart/quantityUp?cartItemId='+cartItemId, options);
    
    if (response.status === 200) { // success
        //updateQuantity(cartItemId);
        // refresh page to refresh the list
        window.location.reload();   // Note this is supposedly a bad way of doing things. Instead we should be updating using the DOM.
    } else {
    }
}


async function decreaseQty(cartItemId) {
    const options = {
        method: 'POST'
    };
    
    const response = await fetch( '/cart/quantityDown?cartItemId='+cartItemId, options);
    
    if (response.status === 200) { // success
        //updateQuantity(cartItemId);
        // refresh page to refresh the list
        window.location.reload();   // Note this is supposedly a bad way of doing things. Instead we should be updating using the DOM.
    } else {
    }
}



function confirmRemoveItem(cartItemId) {
  if (confirm("Are you sure you want to remove this item from your cart?")) {
      removeItem(cartItemId);
  }
}

async function removeItem(cartItemId) {
    const options = {
        method: 'POST'
    };
    
    const response = await fetch( '/cart/removeItem?cartItemId='+cartItemId, options);
    
    if (response.status === 200) { // success
        //updateQuantity(cartItemId);
        // refresh page to refresh the list
        window.location.reload();   // Note this is supposedly a bad way of doing things. Instead we should be updating using the DOM.
    } else {
    }
}
