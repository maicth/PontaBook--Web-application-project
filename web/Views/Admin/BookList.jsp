<%-- 
    Document   : BookUpdate
    Created on : Jun 25, 2022, 10:21:02 PM
    Author     : DELL
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="Models.*"%>
<%@page import="DAL.*"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>PontaBook</title>
        <link rel="icon" href="images/favicon.ico" size="16x16" type="image/x-icon">
        <link rel="stylesheet" href="CSS/homePage.css"/>
        <link rel="stylesheet" href="CSS/bookList.css"/>
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
    </head>
    <body>
        <header>
            <div class="container">
                <a href="Home" class="page-name"><span>PontaBook</span></a>  
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
        <div class="row">
            <div class="admin-menu">
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
            <div class="left">               
                <a class="add-prod" href="add">Thêm sản phẩm</a>
                <h3>Danh sách sản phẩm</h3>
                <table>
                    <tr>
                            <td>Book ID</td>
                            <td>Title</td>
                            <td>Publisher</td> 
                            <td></td>
                            <td></td>
                    </tr>
                    <c:if test="${requestScope.Books != null && requestScope.CP != null}">
                        <c:forEach items="${requestScope.Books}" var="b"
                                   begin="${CP.begin}" end="${CP.end}">
                            <tr>
                                <td>${b.id}</td>
                                <td>${b.title}</td>
                                <td>${requestScope.PublisherDAO.publisherHm.get(b.publisherId).name}</td>
                                <td><a href="book-update?type=0&deleteId=${b.id}" 
                                       onclick="return window.confirm('Bạn có chắc chắn muốn xóa sản phẩm này?');">Xóa</a></td>
                                <td><a href="book-update?type=1&updateId=${b.id}">Chỉnh sửa</a></td>
                            </tr>
                        </c:forEach>
                    </c:if>
                </table>
                <div>
                    <c:if test="${requestScope.Books != null && requestScope.CP != null}">
                        <form action=<c:if test="${param.q != null}">'${request.getQueryString}'</c:if>
                              <c:if test="${param.q == null}">'book-list'</c:if> method="post">
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
            
            <div class="right">           
                <form action="book-update" method="post" enctype="multipart/form-data"
                <c:if test="${requestScope.bookUpdate == null}">hidden</c:if>
                >
                    <h3>Chỉnh sửa thông tin sản phẩm</h3>
                    <table>
                        <tr>
                            <td>Mã số sách</td>
                            <td><input class="for-mat" type="text" name="id" value="${bookUpdate.id}" readonly></td>
                        </tr>
                        <tr>
                            <td>Tiêu đề</td>
                            <td><input class="for-mat" type="text" name="title" value="${bookUpdate.title}" required></td>
                        </tr>
                        <tr>
                            <td>Nhà xuất bản</td>
                            <td><select class="for-mat" name="publisherId" required>
                                <c:forEach items="${requestScope.PublisherDAO.publisherHm}" var="val">
                                    <option value="${val.key}"
                                        ${val.key == bookUpdate.publisherId?"selected":""}>
                                        ${val.value.name}
                                    </option>
                                </c:forEach>
                            </select></td>
                        </tr>
                        <tr>
                            <td>Tác giả</td>
                            <td><select class="for-mat" name="authorId" required>
                                    <c:forEach items="${requestScope.AuthorDAO.authorHm}" var="val">
                                        <option value="${val.key}"
                                            ${val.key == bookUpdate.authorId?"selected":""}>
                                            ${val.value.name}
                                        </option>
                                    </c:forEach>
                                </select></td>
                        </tr>
                        <tr>
                            <td>Ảnh minh họa</td>
                            <td>
                                <input class="for-mat" type="text" name="oldImage" value="${bookUpdate.image}"><br/>
                                <input type="file" name="newImage">
                            </td>
                        </tr>
                        <tr>
                            <td>Giá gốc</td>
                            <td><input class="for-mat" type="number" name="price" onkeypress="return validate(event)" value="${bookUpdate.price}" required></td>
                        </tr>
                        <tr>
                            <td>Ngày xuất bản</td>
                            <td><input class="for-mat" type="date" name="publishDate" value="${bookUpdate.publishDate}" required></td>
                        </tr>
                        <tr>
                            <td>Mô tả sản phẩm</td>
                            <td><input class="for-mat" type="text" name="description" value="${bookUpdate.description}"></td>
                        </tr>
                        <tr>
                            <td>Kích thước</td>
                            <td><input class="for-mat" type="text" name="size" value="${bookUpdate.size}"></td>
                        </tr>
                        <tr>
                            <td>Số lượng</td>
                            <td><input class="for-mat" type="number" onkeypress="return validate(event)" name="quantity" value="${bookUpdate.quantity}" required></td>
                        </tr>
                        <tr>
                            <td>Giảm giá</td>
                            <td><input title="Chiết khấu từ 0 - 100%" class="for-mat" type="number" min='0' max="100" onkeypress="return validate(event)" name="discount" required
                                       value=<fmt:formatNumber value = "${bookUpdate.discount*100}" type="number" maxFractionDigits="0"/>></td>
                        </tr>
                        <tr>
                            <td>Trạng thái</td>
                            <!--<td><input type="text" name="status" value="1"></td>-->
                            <td><input type="radio" name="status" value="1" ${bookUpdate.status?"checked":""}>Hiển thị sản phẩm
                                <input type="radio" name="status" value="0" ${bookUpdate.status?"":"checked"}>Ẩn sản phẩm<br/>
                            </td>
                        </tr> 
                        <tr>
                            <td>Danh mục sản phẩm</td>
                            <td>
                                <div class="CheckBox">
                                    <c:forEach items="${requestScope.CDAO.menu}" var="c">
                                        <span class="cateCheckBox">
                                            <input type="checkbox" name="category" value="${c.id}" 
                                                   <c:forEach items="${requestScope.bCate}" var="cate">
                                                        <c:if test="${c.id == cate.id}">checked="checked"</c:if>
                                                   </c:forEach>
                                            >${c.name}
                                        </span>
                                    </c:forEach>
                                </div>
                            </td>
                        </tr>
                    </table>
                    <input type="submit" value="Lưu">
                </form>
            </div>
        </div>
    </body>
</html>
