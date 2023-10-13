/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */


function decrease(id) {
    var q = parseInt(document.getElementById(id).value);
    if (q > 1) {
        document.getElementById(id).value = q - 1;
    }
}

function increase(id, quan) {
    var q = parseInt(document.getElementById(id).value);
    if (q < quan) {
        document.getElementById(id).value = q + 1;
    } else{
        window.alert("Số lượng không có sẵn");
    }
}

function validate(event) {
    if (event.charCode >= 48 && event.charCode <= 57) {
        return true;
    }
    return false;
}

