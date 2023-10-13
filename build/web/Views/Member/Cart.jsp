<%-- 
    Document   : Cart
    Created on : Jul 7, 2022, 9:59:15 PM
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
        <script src="Js/cart.js"></script>
        <style>
            input::-webkit-outer-spin-button,
            input::-webkit-inner-spin-button {
                -webkit-appearance: none;
                margin: 0;
            }
            a{
                text-decoration: none;
            }
        </style>
        <script>
            function checkQuantity(id, quan, element){
                var oldX = parseInt(element.defaultValue);
                var x = parseInt(document.getElementById(id).value);
                if (x <= 0 || x > quan) {
                    window.alert("Số lượng không có sẵn");
                    element.value = oldX;
                }
                else{
                    element.value = x;
                    document.getElementById("myForm").submit();
                }
            };
            
            function doDelete(id){
                window.location.href("cart?deleteId=" + id);
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
                        
        <p  style="width: 80%; margin: 15px auto; text-transform: uppercase; font-size: 16px">Giỏ hàng (${requestScope.size} sản phẩm)</p>
        
        <c:if test="${requestScope.items == null}">
        <div class="cart-empty">           
            <img src="images/book-shopping.jpg" width="400px" style="display: table; margin: 0 auto">
            <p style="text-align: center"> Chưa có sản phẩm nào trong giỏ hàng của bạn.</p>
            <a href="Home" class="shopping-now">Mua sắm ngay</a>
        </div>
        </c:if>
            
        <c:if test="${requestScope.items != null}">
        <div class="cart-container">
            <c:set var="total" value="0"/>
            <div class="left">
                <p>Danh sách sản phẩm</p>
                <table>
                    <tr>
                        <td style="width: 300px;">Tên sản phẩm</td>
                        <td style="width: 150px;">Đơn giá</td>
                        <td>Số lượng</td>
                        <td style="width: 100px;">Thành tiền</td>
                    </tr>
                    
                <c:forEach items="${requestScope.items}" var="item">
                    <tr>
<!--                        <td>
                            <input type="checkbox" name="choosedItem" onclick="calTotal()">
                        </td>-->
                        <td>
                            <a href="BookDetails?bid=${item.b.id}"><img src="${item.b.image}" width="80px" height="80px"></a>
                            <a href="BookDetails?bid=${item.b.id}">${item.b.title}</a>
                        </td>
                        <td>
                            <c:if test="${item.b.discount > 0}">
                                <fmt:formatNumber value = "${(1-item.b.discount) * item.b.price}" type="number" maxFractionDigits="0" currencyCode="VND"/> đ
                                <span style="color: #333333; font-size: 13px; padding-left: 10px" ><del><fmt:formatNumber value = "${item.b.price}" currencyCode="VND"/> đ</del></span>
                            </c:if>           
                            <c:if test="${item.b.discount <= 0 || item.b.discount == null}"> 
                                <span><fmt:formatNumber value = "${item.b.price}" currencyCode="VND"/> đ</span>
                            </c:if>
                        </td>
                        <td>
                            <form action="cart" method="post" id="myForm">
                            <div class="input-counter">
                                <span class="minus-btn">
                                    <input type="submit" value="-" onclick="decrease(${item.b.id})">
                                    <!--<img src="images/minus.png" width="10px" onclick="decrease(${item.b.id})">-->
                                </span>
                                
                                <input type="number" id="${item.b.id}" value="${item.quantity}" min="1" max="${item.b.quantity}" 
                                       onkeypress="return validate(event)" name="updateQuantity"
                                       onchange="return checkQuantity(${item.b.id},${item.b.quantity}, this)"/>                                                            
                                <span class="plus-btn">
                                    <input type="submit" value="+" onclick="increase(${item.b.id}, ${item.b.quantity})">
                                    <!--<img src="images/plus.png" width="10px" onclick="increase(${item.b.id}, ${item.b.quantity})">-->
                                </span>
                            </div>
                            <input type="text" name="bid" value="${item.b.id}" hidden/>
                            <input type="text" name="uid" value="${sessionScope.account.id}" hidden/>
                            </form>
                        </td>
                        <td>
                            <c:set var="money" value="${(1-item.b.discount) * item.b.price * item.quantity}"/>
                            <c:set var="total" value="${total + money}"/>
                            <fmt:formatNumber value = "${money}" type="number" maxFractionDigits="0" currencyCode="VND"/> đ
                            
                        </td>
                        <td>
                            <a href="cart?deleteId=${item.b.id}" 
                               onclick="return window.confirm('Bạn có chắc chắn muốn xóa sản phẩm này?')">
                                <img src="images/delete.jpg" width="25px">
                            </a>
                            </td>
                    </tr>
                </c:forEach>
                </table>
            </div>
            <div class="right">
                <div>
                <span>Thành tiền</span>
                <span style="float:right"><fmt:formatNumber value = "${total}" type="number" maxFractionDigits="0" currencyCode="VND"/> đ</span>
                </div>
                <div style="margin-top: 15px">
                <span>Phí vận chuyển</span>
                <span style="float:right">0 đ</span>
                </div>           
                <hr/>
                <div>
                    <span>Tổng số tiền</span>
                    <span style="float:right"><fmt:formatNumber value = "${total}" type="number" maxFractionDigits="0" currencyCode="VND"/> đ</span>
                </div>

                    <a href="checkout" class="order-now" >ĐẶT HÀNG </a>

            </div>
        </div>
        </c:if>
    </body>
</html>
