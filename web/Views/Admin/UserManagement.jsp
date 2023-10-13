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
                    <li><a href="ordermanage">Quản lý đơn hàng</a></li>
                    <li><a href="publisher">Nhà xuất bản</a></li>
                    <li><a href="categories">Danh mục sản phẩm</a></li>
                    <li><a href="authors">Tác giả</a></li>

                </ul>
            </div>
            <div class="right">
                <h3>Danh sách Người dùng</h3>
                <table>
                    <tr>
                        <td>Mã số</td>
                        <td>Tên đăng nhập</td>
                        <td>Email</td>
                        <td>Họ tên</td>
                        <td>Vai trò trong trang</td>
                    </tr>
                    <c:forEach items="${requestScope.adao}" var="a">
                        <form action="users" method="post">
                            <tr>
                                <td>${a.id}</td>
                                <td>${a.username}</td>
                                <td>${a.email}</td>           
                                <td>${a.name}</td>
                                <td><select name="role">
                                        <option value="0" ${a.role == 0?"selected":""}>Admin</option>
                                        <option value="1" ${a.role == 1?"selected":""}>Người dùng</option>
                                    </select>
                                </td>
                                <td><input type="submit" value="Lưu"></td>
                            <input type="text" name="id" value="${a.id}" hidden>
                            </tr>
                        </form>
                    </c:forEach>
                </table>
            </div>
    </body>
</html>
