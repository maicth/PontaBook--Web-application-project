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
            function addPub(element){
                var x = document.getElementById("new-pub");
                if(element.checked){
                    x.style.display = 'block';
                    document.getElementById("name").setAttribute("required", "");
                    document.getElementById("email").setAttribute("required", "");
                } else{
                    x.style.display = 'none';
                    document.getElementById("name").removeAttribute("required");
                    document.getElementById("email").removeAttribute("required");
                    }

                }

                function hideForm() {
                    var x = document.getElementById("check-pub");
                    x.checked = false;
                    addPub(x);

                }

                function showForm() {
                    var x = document.getElementById("check-pub");
                    x.checked = true;
                    addPub(x);
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
                    <li><a href="authors">Tác giả</a></li><!--  -->

                </ul>
            </div>
            <div class="right">
            <h3>Danh sách NXB</h3>
            <table>
                <tr>
                    <td>Mã số</td>
                    <td>Tên NXB</td>
                    <td>Email</td>
                    <td>Địa chỉ</td>
                    <td>Số điện thoại</td>
                </tr>
            <c:forEach items="${requestScope.pdao.publisherHm}" var="p">
                <form action="publisher" method="post">
                <tr>
                    <td>${p.value.id}</td>
                    <td><input type="text" name="name" value="${p.value.name}"></td>
                    <td><input type="email" pattern=".+@.+" name="email" value="${p.value.email}"></td>
                    <td><input type="text" name="add" value="${p.value.address}"></td>
                    <td><input type="text" name="phone" value="${p.value.phone}"></td>              
                    <td><input type="submit" value="Lưu"></td>
                    <input type="text" name="id" value="${p.value.id}" hidden>
                </tr>
                </form>
            </c:forEach>
            </table>
            
            <span><input id="check-pub" type="checkbox" onclick="addPub(this)"> Thêm địa chỉ</span>
            <div class="new-pub" id="new-pub" style="display: none">
                <form action="publisher" method="post">
                    <table>
                        <tr>
                            <td>Tên NXB</td>
                            <td><input type="text" id="name" name="name"></td>
                        </tr>
                        <tr>
                            <td>Email</td>
                            <td><input type="email" pattern=".+@.+" id="email" name="email" ></td>
                        </tr>
                        <tr>
                            <td>Địa chỉ</td>
                            <td><input type="text" name="add"></td>
                        </tr>
                        <tr>
                            <td>Số điện thoại</td>
                            <td><input type="text" pattern="[0-9]{10,12}" name="phone"></td>
                        </tr>                           
                </table>    
                <input type="button" value="Hủy" onclick="hideForm()">
                <input type="submit" value="Lưu">
            </form>
            </div>
            </div>
    </body>
</html>
