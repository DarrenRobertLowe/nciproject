/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/javascript.js to edit this template
 */


$('document').ready(function(){
    var password = document.getElementById("userPass");
    var confirmPassword = document.getElementById("confirmPassword");
    
    function validatePassword(){
        // if passwords don't match, show a message, else don't show a message.
        if (password.value !== confirmPassword.value) {
            confirmPassword.setCustomValidity("Passwords Don't Match!");
        } else {
            confirmPassword.setCustomValidity('');
        }
    }
    
    // run the validatePassword() method whenever the passwords change.
    password.onchange = validatePassword;
    confirmPassword.onkeyup = validatePassword;
});