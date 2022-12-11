
function calculate(){
    var price = document.getElementById("price").value;
    var quantity = document.getElementById("quantity").value;

    var result = (quantity * price);
    document.getElementById('subtotal').innerHTML = ""+result.toFixed(2);//.value = result;
}
