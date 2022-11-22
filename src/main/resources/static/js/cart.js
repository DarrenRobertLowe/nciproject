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