<%-- 
    Document   : Home.jsp
    Created on : Jun 22, 2022, 9:11:03 AM
    Author     : DELL
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page import="Models.*"%>
<%@page import="DAL.*"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>PontaBook</title>
        <link rel="icon" href="images/favicon.ico" size="16x16" type="image/x-icon">
        <link rel="stylesheet" href="CSS/homePage.css" type="text/css"/>
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
        <div class="category-container">
            <div class="category">     
                <div class="product-cat">
                    <c:forEach items="${requestScope.CategoryDAO.menu}" var="m">
                       <a href="search?type=0&category=${m.id}"><span>${m.name}</span></a>
                    </c:forEach>
                </div>
            </div>
        </div>
        
        
        <div class="product-container">
            <div class="products">
                <c:if test="${requestScope.books != null && requestScope.CP != null}">
                    <c:forEach items="${requestScope.books}" var="b"
                               begin="${CP.begin}" end="${CP.end}">                  
                        <div class="product-item" title="${b.title}">
                            <div class="product-thumb">
                                <a href="BookDetails?bid=${b.id}">
                                    <img src="${b.image}">
                                </a>
                            </div>
                            <div class="product-info">
                                <div > 
                                    <a href="BookDetails?bid=${b.id}" class="product-name"><span> ${b.title}</span></a> 
                                </div>
                                <div class="product-price">
                                    <c:if test="${b.discount > 0}">
                                        <fmt:formatNumber value = "${(1-b.discount) * b.price}" type="number" maxFractionDigits="0" currencyCode="VND"/> đ
                                        <span style="color: #333333; font-size: 13px; padding-left: 10px" ><del><fmt:formatNumber value = "${b.price}" currencyCode="VND"/> đ</del></span>
                                        <span class="circle" style="color: #fff"><fmt:formatNumber value = "${b.discount}" type="percent"/></span>
                                    </c:if>           
                                    <c:if test="${b.discount <= 0 || b.discount == null}"> 
                                        <span><fmt:formatNumber value = "${b.price}" currencyCode="VND"/> đ</span>
                                    </c:if>


                                </div>
                            </div> 
                        </div>
                    </c:forEach>
                </c:if>
            </div>
            <div>
                <c:if test="${requestScope.books != null && requestScope.CP != null}">
                    <form action="Home" method="post">
                    <c:if test="${CP.cp != 0}">
                        <input type="submit" name="home" value="Home">
                        <input type="submit" name="pre" value="Pre">
                    </c:if>
                    <c:forEach begin="${CP.pageStart}" end="${CP.pageEnd}" var="i">
                        <input type="submit" name="btn${i}" value="${i + 1}">
                    </c:forEach>
                    <c:if test="${CP.cp != CP.np - 1}">
                        <input type="submit" name="next" value="Next">
                        <input type="submit" name="end" value="End">
                    </c:if>
                    <input type="text" hidden name="cp" value="${CP.cp}">
                    <input type="text" hidden name="np" value="${CP.np}">
                    </form>
                </c:if>
            </div>
        </div>
        <div class="footer">
            <div class="container">
                <h2>Footer</h2>
            </div>
        </div>
    </body>
</html>
