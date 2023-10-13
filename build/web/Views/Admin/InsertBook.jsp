<%-- 
    Document   : InsertBook
    Created on : Jun 28, 2022, 9:42:52 AM
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
        <script src="Js/cart.js"></script>
        <style>
            tr, td {
                padding: 10px;
                padding-right: 20px;
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
                       
        <div class="insert-container">
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
                <h3>Nhập thông tin sản phẩm</h3>
                <form action="add" method="post" enctype="multipart/form-data">
                    <table>
                        <tr>
                            <td>Tiêu đề</td>
                            <td><input type="text" name="title" required></td>
                        </tr>
                        <tr>
                            <td>Nhà xuất bản</td>
                            <td><select name="publisherId">
                                <c:forEach items="${requestScope.pdao.publisherHm}" var="val">
                                    <option value="${val.key}">
                                        ${val.value.name}
                                    </option>
                                </c:forEach>
                                </select>
                        </tr>
                        <tr>
                            <td>Tác giả</td>
                            <td><select name="authorId">
                                <c:forEach items="${requestScope.adao.authorHm}" var="val">
                                    <option value="${val.key}">
                                        ${val.value.name}
                                    </option>
                                </c:forEach>
                                </select>
                        </tr>
                        <tr>
                            <td>Ảnh minh họa</td>
                            <td><input type="file" name="coverImage"></td>
                        </tr>
                        <tr>
                            <td>Giá gốc</td>
                            <td><input type="number" name="price" onkeypress="return validate(event)" value="0"  required></td>
                        </tr>
                        <tr>
                            <td>Ngày xuất bản</td>
                            <td><input type="date" name="publishDate" required></td>
                        </tr>
                        <tr>
                            <td>Mô tả sản phẩm</td>
                            <td><input type="text" name="description"></td>
                        </tr>
                        <tr>
                            <td>Kích thước</td>
                            <td><input type="text" name="size"></td>
                        </tr>
                        <tr>
                            <td>Số lượng</td>
                            <td><input type="text" name="quantity" onkeypress="return validate(event)" value="1" required></td>
                        </tr>
                        <tr>
                            <td>Giảm giá</td>
                            <td><input type="number" name="discount" value="0" min="0" max="100"
                                       onkeypress="return validate(event)"></td>
                        </tr>
                        <tr>
                            <td>Trạng thái</td>
                            <!--<td><input type="text" name="status" value="1"></td>-->
                            <td><input type="radio" name="status" value="1" checked>Hiển thị sản phẩm
                                <input type="radio" name="status" value="0">Ẩn sản phẩm<br/>
                            </td>

                        </tr>
                        <tr>
                            <td>Danh mục sản phẩm</td>
                            <td>
                                <div class="CheckBox">
                                <c:forEach items="${requestScope.CDAO.menu}" var="c">
                                    <span class="cateCheckBox">
                                        <input type="checkbox" name="category" value="${c.id}" >${c.name}
                                    </span>
                                </c:forEach>
                            </div>
                        </td>
                    </tr>
                    </table>
                    <input type="submit" value="Thêm">
                </form>
            </div>
        </div>
    </body>
</html>
