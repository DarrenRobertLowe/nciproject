/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/javascript.js to edit this template
 */


function markAsReady(subOrderId) {
    const params = {
        param1:subOrderId
    };
    
    const options = {
        method: 'POST'//,
        //body: JSON.stringify( params )  
    };
    
    fetch( '/webstoredb/markSubOrderAsReady?subOrderId='+subOrderId, options)
        .then( response => response.json() )
        .then( response => {
        //alert('/webstoredb/markSubOrderAsReady?subOrderId='+subOrderId);
    } );
}





