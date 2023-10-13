<%-- 
    Document   : Orders
    Created on : Jul 17, 2022, 12:03:45 AM
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
                <h2>Chi tiết đơn hàng</h2>
                <hr/>
            <c:forEach items="${requestScope.orders}" var="o">
                <c:set var="total" value="0"/>
                ${o.id} | ${o.add.name} | ${o.orderDate} | ${o.nstatus}<br/>                          
                <c:forEach items="${o.odetails}" var="item">
                    <table>
                        <tr>
                            <td style="width: 20%"><a href="BookDetails?bid=${item.b.id}"><img src="${item.b.image}" width="150px"></a></td>
                            <td style="width: 50%"><a href="BookDetails?bid=${item.b.id}">${item.b.title}</a></td>
                            <td style="width: 15%">
                                <fmt:formatNumber value = "${(1-item.discount) * item.b.price}" type="number" 
                                                  maxFractionDigits="0" currencyCode="VND"/> đ <br/>
                                <c:if test="${item.discount > 0}">                      
                                    <span style="color: #333333; font-size: 13px" ><del><fmt:formatNumber value = "${item.b.price}" currencyCode="VND"/> đ</del></span>
                                </c:if>  
                            </td>
                            <td>${item.quantity}</td>
                            <td style="width: 15%"><fmt:formatNumber value = "${(1-item.discount) * item.b.price * item.quantity}" type="number" 
                                              maxFractionDigits="0" currencyCode="VND"/> đ <br/></td>
                                <c:set var="money" value="${(1-item.discount) * item.b.price * item.quantity}"/>
                                <c:set var="total" value="${total + money}"/>
                        </tr>
                    </table>
                </c:forEach>
                <h3>Tổng tiền: <fmt:formatNumber value = "${total+15000}" type="number" maxFractionDigits="0" currencyCode="VND"/> đ</h3>
                <hr/>
            </c:forEach>
            </div>
        </div>
    </body>
</html>
