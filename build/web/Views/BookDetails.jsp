<%-- 
    Document   : BookDetails
    Created on : Jun 26, 2022, 11:48:34 PM
    Author     : DELL
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page import="Models.User" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>PontaBook</title>
        <link rel="icon" href="images/favicon.ico" size="16x16" type="image/x-icon">
        <link rel="stylesheet" href="CSS/homePage.css"/>
        <link rel="stylesheet" href="CSS/bookDetail.css"/>
        <script src="Js/cart.js" type="text/javascript"></script>
        <script>
            function checkQuantity(quan, id) {
            var acc = "<%=session.getAttribute("account")%>";
            if (acc === "null") {
                window.alert("Bạn cần đăng nhập để thêm giỏ hàng");
                return false;
            }
            
            if(document.getElementById(id).value == ""){
                window.alert("Vui lòng nhập số lượng sản phẩm!");
                return false;
            }
            else {
                var x = parseInt(document.getElementById(id).value);

                if (x <= 0 || x > quan) {
                    window.alert("Số lượng không có sẵn");
                    return false;
                }

                return true;
            }

        }
        </script>
    </head>
    <body>
        <c:set scope="request" var="b" value="${requestScope.book}"></c:set>
        
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
        
        <div class="category-container">
            <h3 style="background-color: #FEB07D"> <a href="Home">Trang chủ</a> > Danh mục sản phẩm > </h3>
        </div>
        <form action="cart" method="post" onsubmit="return checkQuantity(${b.quantity}, ${b.id});" >
            <div class="row">
                <div class="left">
                    <a href="${b.image}"><img src="${b.image}" width="300px" height="300px"></a>             
                <input type="submit" class="add-cart" value="Thêm vào giỏ hàng"/> 
                <input type="text" name="bid" value="${b.id}" hidden>
                <input type="text" name="uid" value="${sessionScope.account.id}" hidden>
                </div>
                <div class="right">
                    <div class="b-title" >${b.title}</div>
                    <div class="description">
                        <table>
                            <tr>
                                <td>Nhà xuất bản: <a href="search?type=2&pub=${b.publisherId}">${requestScope.PublisherDAO.publisherHm.get(b.publisherId).name}</a></td>
                                <td>Tác giả: <a href="search?type=1&authId=${b.authorId}">${requestScope.AuthorDAO.authorHm.get(b.authorId).name}</a></td>
                            </tr>
                            <tr>
                                <td>Kích thước: ${b.size}</td>
                                <td>Hình thức bìa:</td>
                            </tr>
                        </table>
                    </div>

                    <div class="price">
                        <c:if test="${b.discount > 0}">
                            <fmt:formatNumber value = "${(1-b.discount) * b.price}" type="number" maxFractionDigits="0" currencyCode="VND"/> đ
                            <span style="color: #333333; font-size: 16px; padding-left: 10px; vertical-align: middle" ><del><fmt:formatNumber value = "${b.price}" currencyCode="VND"/> đ</del></span>
                            <span class="dicount"><fmt:formatNumber value = "${b.discount}" type="percent"/></span>
                        </c:if>           
                        <c:if test="${b.discount <= 0 || b.discount == null}"> 
                            <span><fmt:formatNumber value = "${b.price}" currencyCode="VND"/> đ</span>
                        </c:if>
                    </div>

                    <div class="quantity">
                        <span style="padding-right: 20px">SỐ LƯỢNG</span>
                        <input type="button" onclick="decrease(${b.id})" value="-"> 
                            <input type="number" onkeypress="return validate(event)" id="${b.id}" name="quantity" value="1" min="1"/>
                            <input type="button" onclick="increase(${b.id}, ${b.quantity})" value="+">
                    </div>
                </div>    
            </div>
        </form>
        <div class="row2">
            <div class="desciption">
                <h2>Chi tiết sản phẩm</h2>
                <table>
                    <tr>
                        <td>Tác giả</td>
                        <td><a href="search?type=1&authId=${b.authorId}">${requestScope.AuthorDAO.authorHm.get(b.authorId).name}</a></td>
                    </tr>
                    <tr>
                        <td>Nhà xuất bản</td>
                        <td><a href="search?type=2&pub=${b.publisherId}">${requestScope.PublisherDAO.publisherHm.get(b.publisherId).name}</a></td>
                    </tr>
                    <tr>
                        <td>Năm xuất bản</td>
                        <td>
                            <fmt:formatDate type = "date" pattern="dd - MM - yyyy" value = "${b.publishDate}"/>                           
                        </td>
                    </tr>
                    <tr>
                        <td>Kích thước</td>
                        <td>${b.size}</td>
                    </tr>
                    <tr>
                        <td>Nằm trong danh mục</td>
                        <td>
                            <ul>
                                <c:forEach items="${requestScope.CDAO}" var="c">
                                    <li>
                                        <a href="search?type=0&category=${c.id}">${c.name}
                                    </li>
                                </c:forEach>
                            </ul>
                        </td>
                    </tr>
                    <tr>
                        <td></td>
                        <td></td>
                    </tr>
                </table>
                <hr>
                <h2>Mô tả sản phẩm</h2>
                <span>${b.description}</span>
            </div>
        </div>
            
        <div class="footer">
            <div class="container">
                <h2>Footer</h2>
            </div>
        </div>
    </body>
</html>
