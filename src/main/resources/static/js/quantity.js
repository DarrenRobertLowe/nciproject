

function increaseQty(cartItemId){
    window.location.reload();
    //increaseQtyFetch(cartItemId);
    
}

function decreaseQty(cartItemId){
    window.location.reload();//decreaseQtyFetch(cartItemId);
    
}


async function updateOutput() {
    alert("I am groot");
    $.get("quantity-fragment").done(function (fragment) {
        console.log(fragment);
        $("#quantity").replaceWith(fragment);
    });
}


async function increaseQtyFetch(cartItemId) {
    const options = {
        method: 'POST'
    };
    
    const response = await fetch( '/cart/quantityUp?cartItemId='+cartItemId, options);
    
    if (response.status === 200) { // success
        // refresh page to refresh the list
        //window.location.reload();   // Note this is supposedly a bad way of doing things. Instead we should be updating using the DOM.
    } else {
    }
}


async function decreaseQtyFetch(cartItemId) {
    const options = {
        method: 'POST'
    };
    
    const response = await fetch( '/cart/quantityDown?cartItemId='+cartItemId, options);
    
    if (response.status === 200) { // success
        // refresh page to refresh the list
        //updateOutput(); //window.location.reload();   // Note this is supposedly a bad way of doing things. Instead we should be updating using the DOM.
    } else {
    }
}