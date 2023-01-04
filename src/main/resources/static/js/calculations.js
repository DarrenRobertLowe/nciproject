

function calculate(){
    var price = document.getElementById("price").value;
    var quantity = document.getElementById("quantity").value;

    var result = (quantity * price);
    document.getElementById('subtotal').innerHTML = ""+result.toFixed(2);//.value = result;
}


function calcIndex(id, price, quantity){
    var result = (quantity * price);
    document.getElementById(id).innerHTML = "Total €"+result.toFixed(2);//.value = result;
}




$(document).ready(function(){
    $('.minus').click(function(e){
        e.preventDefault();                             // prevent form submit

        var quantity = parseInt($('#quantity').val());  // Get the quantity

        // Decrement
        if(quantity > 1){
            $('#quantity').val(quantity - 1);
        }
    });
    
    
    $('.plus').click(function(e){
        e.preventDefault(); // prevent form submit
        
        var quantity = parseInt($('#quantity').val());  // Get the quantity
        
        // Increment
        $('#quantity').val(quantity + 1);
    });
});