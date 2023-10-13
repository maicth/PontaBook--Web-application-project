<%-- 
    Document   : Address
    Created on : Jul 17, 2022, 1:32:51 AM
    Author     : DELL
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>PontaBook</title>
        <link rel="icon" href="images/favicon.ico" size="16x16" type="image/x-icon">
        <link rel="stylesheet" href="CSS/homePage.css" type="text/css"/>
        <link rel="stylesheet" href="CSS/account.css" type="text/css"/>
        <style>
            table, tr, td {
                border: 1px solid black
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

            function hideForm() {
                var x = document.getElementById("check-add");
                x.checked = false;
                addNewAddress(x);

            }
            
            function showForm() {
                var x = document.getElementById("check-add");
                x.checked = true;
                addNewAddress(x);
            }

            function getAddressId() {
                var adds = document.getElementsByName("add");
                for (i = 0; i < adds.length; i++) {
                    if (adds[i].checked)
                        document.getElementById("buy-address").value
                                = adds[i].value;
                }
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
                        <!--check whether user login or not? if not then login/ 
                        if yes then user account-->
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
        <div class="row">
            <div class="left">
                <ul>
                    <li><span style="font-size: 18px; padding-left:16px; padding-top: 15px; display: table">TÀI KHOẢN</span></li><hr/>
                    <li><a href="account">Thông tin tài khoản</a></li>
                    <li><a href="orders">Đơn hàng của tôi</a></li>
                    <li><a href="address">Sổ địa chỉ</a></li>
                </ul>
            </div>
            <div class="right">                              
            <h2>Sổ địa chỉ</h2>
            
                <table>
                    <tr>
                        <td>Tên người nhận</td>
                        <td>Số điện thoại</td>
                        <td>Địa chỉ</td>
                    </tr>
                     <c:forEach items="${requestScope.adds}" var="a">
                    <form action="address" method="post">
                    <tr>
                        <td><input type="text" name="name" value="${a.name}"></td> 
                        <td><input type="text" name="phone" value="${a.phone}"></td> 
                        <td><input type="text" name="address" value="${a.add}"></td>
                        <td><input type="submit" value="Lưu">
                            <input type="text" name="addressId" value="${a.id}" hidden></td>
                        <td><a href="address?deleteId=${a.id}">Xóa</a></td>
                    </tr>
                    </form>
                    </c:forEach>
                </table>
            
            <span><input id="check-add" type="checkbox" onclick="addNewAddress(this)"> Thêm địa chỉ</span>
            <div class="new-address" id="new-add" style="display: none">
                <form action="address" method="post">
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
    </body>
</html>
