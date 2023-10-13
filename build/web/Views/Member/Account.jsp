<%-- 
    Document   : Account
    Created on : Jul 9, 2022, 10:46:12 PM
    Author     : DELL
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>PontaBook</title>
        <link rel="icon" href="images/favicon.ico" size="16x16" type="image/x-icon">
        <link rel="stylesheet" href="CSS/homePage.css" type="text/css"/>
        <link rel="stylesheet" href="CSS/account.css" type="text/css"/>
        <script>
            function showChangePwd(element) {  
                var x = document.getElementById("change-pwd");
                if(element.checked){
                    x.style.display = "block";
                    document.getElementById("oldpass").setAttribute("required", "");
                    document.getElementById("newpass").setAttribute("required", "");
                    document.getElementById("newpass2").setAttribute("required", "");
                } else {
                    x.style.display = "none";
                    document.getElementById("oldpass").removeAttribute("required");
                    document.getElementById("newpass").removeAttribute("required");
                    document.getElementById("newpass2").removeAttribute("required");
                }
            }
            
            
            function checknewPass(){
                var pass1 = document.getElementById("newpass").value;
                var pass2 = document.getElementById("newpass2").value;
                
                if(pass1 === pass2){
                    return true;
                } else{
                    document.getElementById("mess-confirm").removeAttribute("hidden");
                    return false;
                }
            }
            
            function cancelMess(){
                document.getElementById("mess-confirm").setAttribute("hidden", "");
                document.getElementById("newpass2").value = "";
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
                <form action="account" method="post" onsubmit="return checknewPass()">
                    <table>
                        <tr <c:if test="${requestScope.mess == null}">hidden</c:if>>
                        <td style="color: red">* ${requestScope.mess}</td>
                        </tr>
                        <tr>
                            <td>Họ tên</td>
                            <td><input type="text" name="name" required value="${sessionScope.account.name}"></td>
                        </tr>
                        <tr>
                            <td>Email</td>
                            <td><input type="email" name="email" pattern=".+@.+" required value="${sessionScope.account.email}"></td>
                        </tr>
                        <tr>
                            <td>Ngày sinh</td>
                            <td><input type="date" name="dob" value="${sessionScope.account.dob}"></td>
                        </tr>
                        <tr>
                            <td>Giới tính</td>
                            <td><input type="radio" name="gender" value="1" ${sessionScope.account.gender?"checked":""}>Nam
                                <input type="radio" name="gender" value="0" ${sessionScope.account.gender?"":"checked"}>Nữ 
                            </td>
                        </tr>                      
                    </table>
                    <div>
                        <input type="checkbox" onclick="showChangePwd(this)"> ĐỔI MẬT KHẨU <br/>
                        <div class="change-pwd" id="change-pwd" style="display: none">
                            <table>
                                <tr>
                                    <td>Mật khẩu hiện tại</td>
                                    <td><input type="password" name="pass" id="oldpass"></td>                  
                                </tr>
                                <tr>
                                    <td>Mật khẩu mới</td>
                                    <td><input type="password" pattern=".{6,}" name="newpass" id="newpass"
                                               accept=" "title="Must contain at least 6 or more characters"></td>
                                </tr>
                                <tr>
                                    <td>Nhập lại mật khẩu mới</td>
                                    <td><input type="password" name="re-newpass" id="newpass2"></td>
                                </tr>
                                <tr id="mess-confirm" hidden>
                                    <td style="color: red;"> * Mật khẩu xác nhận không đúng</td>
                                    <td><input id="cancel" type="button" value="Đóng" onclick="cancelMess()"></td>
                                </tr>
                            </table>
                        </div>
                    </div>
                <input type="submit" value="Lưu thay đổi">
            </form>
            </div>
        </div>
    </body>
</html>
