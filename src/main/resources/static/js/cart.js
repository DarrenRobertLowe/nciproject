/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/javascript.js to edit this template
 */





async function confirmCheckout(customerId) {
    const options = {
        method: 'POST'
    };
    
    const response = await fetch( '/checkout?customerId='+customerId, options);
    
    if (response.status === 200) { // success
        //window.location.reload();   // Note this is supposedly a bad way of doing things. Instead we should be updating using the DOM.
        alert("customerId: " + customerId + " : Order placed successfully!");
    } else {
        alert("Something went wrong");
    }
}


async function updateQuantity(cartItemId) {
    $.get("quantity-fragment?cartItemId=" + cartItemId).done(function (fragment) {
        console.log(fragment);
        $("#quantity-"+cartItemId).replaceWith(fragment);
    });
    /*
    const options = {
        method: 'GET'
    };
    
    const response = await fetch( '/quantity-fragment?cartItemId='+cartItemId, options);
    
    if (response.status === 200) { // success
        // refresh page to refresh the list
        //window.location.reload();   // Note this is supposedly a bad way of doing things. Instead we should be updating using the DOM.
    } else {
    }
    */
}


async function increaseQty(cartItemId) {
    const options = {
        method: 'POST'
    };
    
    const response = await fetch( '/cart/quantityUp?cartItemId='+cartItemId, options);
    
    if (response.status === 200) { // success
        updateQuantity(cartItemId);
        // refresh page to refresh the list
        //window.location.reload();   // Note this is supposedly a bad way of doing things. Instead we should be updating using the DOM.
    } else {
    }
}


async function decreaseQty(cartItemId) {
    const options = {
        method: 'POST'
    };
    
    const response = await fetch( '/cart/quantityDown?cartItemId='+cartItemId, options);
    
    if (response.status === 200) { // success
        updateQuantity(cartItemId);
        // refresh page to refresh the list
        //window.location.reload();   // Note this is supposedly a bad way of doing things. Instead we should be updating using the DOM.
    } else {
    }
}