<%-- 
    Document   : SearchBook
    Created on : Jun 27, 2022, 12:03:16 PM
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
        <link rel="stylesheet" href="CSS/bookDetail.css" type="text/css"/>
        <style>
            .filter{
                width: 80%;
                margin: 0 auto;
                padding: 10px;              
            }
            
            .filter a {
                text-transform: uppercase;
                font-weight: 600;
                font-size: 15px;
                margin-right: 30px;
            }
        </style>
        <script>
            function doAsc(){
                if(${param.sort == null}){
                    document.getElementById("asc").href = "search?sort=asc&${pageContext.request.queryString}";
                } else{
                    var searchParams = new URLSearchParams(window.location.search);
                    searchParams.set("sort", "asc");
                    document.getElementById("asc").href = "search?" + searchParams.toString();
                        
                }
            }
            
            function doDesc(){
                if(${param.sort == null}){
                    document.getElementById("desc").href = "search?sort=desc&${pageContext.request.queryString}";
                } else{
                    var searchParams = new URLSearchParams(window.location.search);
                    searchParams.set("sort", "desc");
                    document.getElementById("desc").href = "search?" + searchParams.toString();
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
                        <c:if test="${sessionScope.account == null}" ><a href="Login">Đăng nhập/ Đăng ký</a></c:if>
                        </li>
                    </ul>
                </div>
            </div>
        </header>
              
        <div class="search-info">
            <div><a href="Home">Trang chủ</a> > ${requestScope.search}</div>
        </div>
        <div class="filter">
            <a id="asc" href="" onclick="doAsc()">Giá tăng dần</a>
            <a id="desc" href="" onclick="doDesc()">Giá giảm dần</a>
        </div>
        <div class="product-container">
            <div class="products">
                <c:if test="${requestScope.blist != null && requestScope.CP != null}">
                    <c:forEach items="${requestScope.blist}" var="b"
                               begin="${CP.begin}" end="${CP.end}">                  
                        <div class="product-item" title="${b.title}">
                            <div class="product-thumb">
                                <a href="BookDetails?bid=${b.id}">
                                    <img src="${b.image}">
                                </a>
                            </div>
                            <div class="product-info">
                                <div > 
                                    <a href="BookDetails?bid=${b.id}" class="product-name"> <span>${b.title}</span></a> 
                                </div>
                                <div class="product-price">
                                    <c:if test="${b.discount > 0}">
                                        <fmt:formatNumber value = "${(1-b.discount) * b.price}" type="number" maxFractionDigits="0" currencyCode="VND"/> đ
                                        <span style="color: #333333; font-size: 13px; padding-left: 10px" ><del><fmt:formatNumber value = "${b.price}" currencyCode="VND"/> đ</del></span>
                                        <div class="circle" style="color: #fff"><fmt:formatNumber value = "${b.discount}" type="percent"/></div>
                                    </c:if>           
                                    <c:if test="${b.discount <= 0 || b.discount == null}"> 
                                        <span><fmt:formatNumber value = "${b.price}" currencyCode="VND"/> đ</span>
                                    </c:if>                                
                                </div>
                            </div> 
                        </div>
                    </c:forEach>
                </c:if>
                <c:if test="${requestScope.blist == null}">
                    <p>0 SẢN PHẨM</p>
                </c:if>
            </div>
            
            <div>
                <c:if test="${requestScope.blist != null &&requestScope.CP != null}">
                <form action="${request.getQueryString}" method="post">
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
    </body>
</html>
