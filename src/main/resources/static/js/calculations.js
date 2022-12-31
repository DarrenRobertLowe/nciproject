

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


