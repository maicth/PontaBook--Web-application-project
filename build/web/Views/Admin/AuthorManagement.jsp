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
        <script>
            function addAuth(element) {
                var x = document.getElementById("new-auth");
                if (element.checked) {
                    x.style.display = 'block';
                    document.getElementById("name").setAttribute("required", "");
                    document.getElementById("email").setAttribute("required", "");
                } else {
                    x.style.display = 'none';
                    document.getElementById("name").removeAttribute("required");
                    document.getElementById("email").removeAttribute("required");
                }

            }

            function hideForm() {
                var x = document.getElementById("check-auth");
                x.checked = false;
                addAuth(x);

            }

            function showForm() {
                var x = document.getElementById("check-auth");
                x.checked = true;
                addAuth(x);
            }
        </script>
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
                <h3>Danh sách Tác giả</h3>
                <table>
                    <tr>
                        <td>Mã số</td>
                        <td>Tên tác giả</td>
                        <td>Email</td>
                    </tr>
                    <c:forEach items="${requestScope.adao.authorHm}" var="a">
                        <form action="authors" method="post">
                            <tr>
                                <td>${a.value.id}</td>
                                <td><input type="text" name="name" value="${a.value.name}"></td>
                                <td><input type="text" name="email" value="${a.value.email}"></td>           
                                <td><input type="submit" value="Lưu"></td>
                            <input type="text" name="id" value="${a.value.id}" hidden>
                            </tr>
                        </form>
                    </c:forEach>
                </table>

                <span><input id="check-auth" type="checkbox" onclick="addAuth(this)"> Thêm Tác giả</span>
                <div class="new-auth" id="new-auth" style="display: none">
                    <form action="authors" method="post">
                        <table>
                            <tr>
                                <td>Tên</td>
                                <td><input type="text" id="name" name="name"></td>
                            </tr>
                            <tr>
                                <td>Email</td>
                                <td><input type="email" id="email"  pattern=".+@.+" name="email" ></td>
                            </tr>                        
                        </table>    
                        <input type="button" value="Hủy" onclick="hideForm()">
                        <input type="submit" value="Lưu">
                    </form>
                </div>
            </div>
    </body>
</html>
