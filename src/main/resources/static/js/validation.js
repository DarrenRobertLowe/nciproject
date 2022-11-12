/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/javascript.js to edit this template
 */



function validate() {
    if (document.loginForm.username.value == "" && document.loginForm.password.value == "") {
        alert("Username and password are required");
        document.loginForm.username.focus();
        return false;
    }
    if (document.loginForm.username.value == "") {
        alert("Username is required");
        document.loginForm.username.focus();
        return false;
    }
    if (document.loginForm.password.value == "") {
        alert("Password is required");
        document.loginForm.password.focus();
        return false;
    }
}

function init() {
    document.getElementById('loginForm').onsubmit = validate;
}
window.onload = init;