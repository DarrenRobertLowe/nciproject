
    function calculate(){
        var price = document.getElementById("price").value;
        var quantity = document.getElementById("quantity").value;
        
        var result = (quantity * price);
        document.getElementById('total').innerHTML = ""+result;//.value = result;
    }
