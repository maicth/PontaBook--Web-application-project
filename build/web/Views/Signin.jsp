<%-- 
    Document   : Login
    Created on : Jun 24, 2022, 5:20:33 PM
    Author     : DELL
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>PontaBook | Sign-in</title>
        <link rel="icon" href="images/favicon.ico" size="16x16" type="image/x-icon">
        <link rel="stylesheet" href="CSS/homePage.css"/>
        <script>
            function doMessage(){
                var pass = document.getElementById("pass").value;
                var pass2 = document.getElementById("pass2").value;
                if(pass !== pass2){
                    window.alert("Nhập mật khẩu không khớp!");
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
                        <li><a href="Login">Đăng nhập/ Đăng ký</a></li>
                    </ul>
                </div>
            </div>
        </header>
        
        
        
        <form action="sign-in" method="post" onsubmit="doMessage()">
            <div class="signup-box">
                <c:if test="${requestScope.msg != null}"><span style="color: red">* ${requestScope.msg}</span></c:if>
                <input type="email" pattern=".+@.+" name="email" placeholder="Email" required><br/>
                <input type="text"  name="username" placeholder="Tên đăng nhập" required><br/>
                <input type="password" id="pass" name="pass" pattern=".{6,}" 
                       title="Must contain at least 6 or more characters" placeholder="Mật khẩu" required><br/>
                <input type="password" id="pass2" name="re-pass" placeholder="Xác nhận mật khẩu" required><br/>
                <input type="submit" value="Đăng ký">                   
            </div>
        </form>                 
    </body>
</html>
