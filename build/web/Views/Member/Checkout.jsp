<%-- 
    Document   : Checkout
    Created on : Jul 9, 2022, 9:16:54 PM
    Author     : DELL
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>PontaBook</title>
        <link rel="icon" href="images/favicon.ico" size="16x16" type="image/x-icon">
        <link rel="stylesheet" href="CSS/homePage.css" type="text/css"/>
        <link rel="stylesheet" href="CSS/cart.css" type="text/css"/>
        <style>
            body {
                font-family: "Helvetica Neue", Helvetica, Arial, sans-serif;
            }
            .checkout-container {
                width: 70%;
                margin: 10px auto;
            }
            
            .address-container{
                width: 100%;
                background-color: #FFF;
                margin: 20px 0;
                padding: 5px 0px;
            }
            
            .shipping-container {
                width: 100%;
                background-color: #FFF;
                margin: 20px 0;
                padding: 10px 0px;
            }
            
            .check-products {
                width: 100%;
                background-color: #FFF;
                margin: 20px 0;
                padding: 5px 0px;
            }
            
            .container2 {
                display: flex;
                position: relative;
                padding-left: 35px;
                margin-bottom: 5px;
                cursor: pointer;
                font-size: 22px;
            }
            
            .container2 ul {
                list-style: none;
                font-size: 16px;
                font-weight: 400;
            }
            
            .container2 li{
                padding: 2px
            }
            
            .new-address{
                padding-left: 35px;
            }
            
            table td, tr {
                font-size: 16px;
            }
        </style>
        <script>
            function addNewAddress(element){
                var x = document.getElementById("new-add");
                if(element.checked){
                    x.style.display = 'block';
                    document.getElementById("name").setAttribute("required", "");
                    document.getElementById("phone").setAttribute("required", "");
                    document.getElementById("address").setAttribute("required", "");
                } else{
                    x.style.display = 'none';
                    document.getElementById("name").removeAttribute("required");
                    document.getElementById("phone").removeAttribute("required");
                    document.getElementById("address").removeAttribute("required");
                }
                
            }
            
            function hideForm(){
                var x = document.getElementById("check-add");
                x.checked = false;
                addNewAddress(x);
                
            }
            
            function getAddressId(){
                var adds = document.getElementsByName("add");
                if(adds.length === 0){
                    window.alert("Vui lòng thêm địa chỉ nhận hàng!");
                    return false;
                }
                for(i = 0; i < adds.length; i++) {
                    if(adds[i].checked)
                    document.getElementById("buy-address").value
                            = adds[i].value;
                }
                return true;
            }
        </script>
    </head>
    <body>
        <header>
            <div class="container">
                <a href="Home" class="page-name"><span>PontaBook</span></a>  
                <div class="search-container">
                    <form action="search?type=3" class="search-bar">
                        <input type="text" name="q" placeholder="Tìm kiếm..." >
                        <button type="submit"><img src="images/search.png" width="20px" height="20px"></button>
                    </form>
                </div>
                <div class="info">
                    <ul>
                        <li><a href="cart">Giỏ hàng</a></li>
                        <li>
                            <c:if test="${sessionScope.account != null}">
                                <div class="dropdown">
                                    <button class="dropbtn">${sessionScope.account.name}</button>
                                    <div class="dropdown-content">
                                        <a href="account">Tài khoản</a>
                                        <c:if test="${sessionScope.account.role == 0}">
                                            <a href="book-list" >Admin</a>
                                        </c:if>
                                        <a href="Logout">Thoát tài khoản</a>
                                    </div>
                                </div>
                            </c:if>
                            <c:if test="${sessionScope.account == null}"><a href="Login">Đăng nhập/ Đăng ký</a></c:if>
                        </li>
                    </ul>
                </div>
            </div>
        </header>
        <div class="checkout-container">
            <c:set var="total" value="0"/>
            <div class="address-container">
                <h3 style="text-transform: uppercase; padding: 5px;">Địa chỉ giao hàng</h3><hr/>
                <div class="list-add">
                <c:if test="${requestScope.adds != null}">
                    <c:forEach items="${requestScope.adds}" var="adr">
                        <label class="container2">
                            <input type="radio" name="add" value="${adr.id}" checked>
                            <ul>
                                <li>${adr.name} | ${adr.phone} | ${adr.add}</li>
                            </ul>
                        </label>
                    </c:forEach>
                </c:if>
                </div>
                
                <span style="padding-left: 35px"><input id="check-add" type="checkbox" onclick="addNewAddress(this)"> Thêm địa chỉ</span>
                <div class="new-address" id="new-add" style="display: none">
                    <form action="listaddress" method="post">
                        <table>
                        <tr>
                            <td>Nguời nhận</td>
                            <td><input type="text" id="name" name="name" value="${sessionScope.account.name}"></td>
                        <tr>
                            <td>Số điện thoại</td>
                            <td><input type="tel" name="phone" id="phone" pattern="[0-9]{10}"></td>
                        </tr>
                        <tr>
                            <td>Địa chỉ nhận hàng</td>
                            <td><input type="text" name="address" id="address"></td>
                        </tr>
                        </table>    
                        <input type="button" value="Hủy" onclick="hideForm()">
                        <input type="submit" value="Lưu địa chỉ">
                    </form>
                </div>
            
            </div>
            <div class="shipping-container">
                <h3 style="text-transform: uppercase">Phương thức vận chuyển</h3><hr/>
                <span style="padding-left: 35px;"><input type="radio" checked> Giao hàng tiêu chuẩn: 15.000đ</span>
            </div>
            <div class="check-products">
                <h3 style="text-transform: uppercase">Kiểm tra lại đơn hàng</h3><hr/>
                <table>
                    <c:forEach items="${requestScope.cart}" var="i">
                        <tr>
                            <td style="width: 20%"><a href="BookDetails?bid=${i.b.id}"><img src="${i.b.image}" width="150px"></a></td>
                            <td style="width: 50%"><a href="BookDetails?bid=${i.b.id}">${i.b.title}</a></td>
                            <td style="width: 15%">
                                <fmt:formatNumber value = "${(1-i.b.discount) * i.b.price}" type="number" 
                                                  maxFractionDigits="0" currencyCode="VND"/> đ <br/>
                                <c:if test="${i.b.discount > 0}">                      
                                <span style="color: #333333; font-size: 13px" ><del><fmt:formatNumber value = "${i.b.price}" currencyCode="VND"/> đ</del></span>
                            </c:if>  
                            </td>
                            <td>${i.quantity}</td>
                            <td style="width: 15%"><fmt:formatNumber value = "${(1-i.b.discount) * i.b.price * i.quantity}" type="number" 
                                                  maxFractionDigits="0" currencyCode="VND"/> đ <br/></td>
                            <c:set var="money" value="${(1-i.b.discount) * i.b.price * i.quantity}"/>
                            <c:set var="total" value="${total + money}"/>
                        </tr>
                    </c:forEach>
                </table>
            </div>
            <div style="background-color: #FFF; display: block; padding-bottom: 20px; margin-bottom: 20px;">
                <div style="display: block; text-align: right;">
                    <table style="font-size: 20px; float: right; width: 100%; padding-right: 50px ">
                        <tr>
                            <th>Thành tiền:</th>
                            <td> <fmt:formatNumber value = "${total}" type="number" maxFractionDigits="0" currencyCode="VND"/> đ</td>
                        </tr>
                        <tr>
                            <th>Phí vận chuyển:</th>
                            <td> 15,000 đ</td>
                        </tr>
                        <tr>
                            <th>Tổng số tiền: </th>
                            <td><fmt:formatNumber value = "${total+15000}" type="number" maxFractionDigits="0" currencyCode="VND"/> đ</td>
                        </tr>
                    </table>
                </div>
                <hr/>
                <div>
                <a href="cart">Quay lại giỏ hàng</a>               
                </div>
            </div>
            <form action="checkout" method="post" onsubmit="return getAddressId()">
                    <input type="submit" value="Xác nhận đơn hàng" style=" font-size: 20px; font-family: nunito-sans, sans-serif, Helvetica, Arial ;float: right; width: 400px; 
                         padding: 10px  ;background-color: #C92127; color: #fff; border-radius: 10px; ">
                    <input type="text" name="buy-address" id="buy-address" value="" hidden>
            </form>
        </div>  
    </body>
</html>
