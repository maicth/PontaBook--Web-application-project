<%-- 
    Document   : PublisherManagement
    Created on : Jul 17, 2022, 4:03:15 PM
    Author     : DELL
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>PontaBook</title>
        <link rel="icon" href="images/favicon.ico" size="16x16" type="image/x-icon">
        <link rel="stylesheet" href="CSS/homePage.css"/>
        <link rel="stylesheet" href="CSS/admin.css"/>
        <style>
            tr, td{
                padding:5px;
            }
        </style>
    </head>
    <body>
        <header>
            <div class="container">
                <a href="book-update" class="page-name"><span>PontaBook</span></a>  
                <div class="search-container">
                    <form action="book-list" class="search-bar">
                        <input type="text" name="q" placeholder="Tìm kiếm..." >
                        <button type="submit"><img src="images/search.png" width="20px" height="20px"></button>
                    </form>
                </div>
                <div class="info">
                    <ul>
                        <li>
                        <c:if test="${sessionScope.account != null}">
                            <div class="dropdown">
                                <button class="dropbtn">${sessionScope.account.name}</button>
                                <div class="dropdown-content">
                                    <a href="account">Tài khoản</a>
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

        <div class="pub-container">
            <div class="left">
                <ul>
                    <li><span style="font-size: 18px; padding-left:16px; padding-top: 15px; display: table">
                            BẢNG ĐIỀU KHIỂN</span></li><hr/>
                    <li><a href="book-list">Quản lý sách</a></li>
                    <li><a href="users">Quản lý user</a></li>
                    <li><a href="ordermange">Quản lý đơn hàng</a></li>
                    <li><a href="publisher">Nhà xuất bản</a></li>
                    <li><a href="categories">Danh mục sản phẩm</a></li>
                    <li><a href="authors">Tác giả</a></li>

                </ul>
            </div>
            <div class="right">
                <h3>Danh sách Đơn hàng</h3>
                <table>
                    <tr>
                        <td>Mã đơn hàng</td>
                        <td>Tài khoản</td>
                        <td>Ngày đặt hàng</td>
                        <td>Trạng thái</td>

                    </tr>
                    <c:forEach items="${requestScope.odao}" var="o">
                        <form action="ordermanage" method="post">
                            <tr>
                                <td>${o.id}</td>
                                <td>${adao.getUser(o.userId).username}</td>
                                <td>${o.orderDate}</td>
                                <c:if test="${o.status < 4}">
                                <td><select name="odstatus">
                                        <c:forEach begin="${o.status}" end="5" var="i">
                                            <option value="${i}">${o.getNstatus(i)}</option>
                                        </c:forEach>
                                    </select>
                                </td>         
                                <td><input type="submit" value="Lưu"></td>
                                </c:if>
                                <c:if test="${o.status >= 4}">
                                    <td>${o.nstatus}</td>
                                </c:if>
                                
                            <input type="text" name="id" value="${o.id}" hidden>
                            </tr>
                        </form>
                    </c:forEach>
                </table>
            </div>
    </body>
</html>
