<%-- 
    Document   : Login
    Created on : Jun 24, 2022, 5:20:33 PM
    Author     : DELL
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>PontaBook | Login</title>
        <link rel="icon" href="images/favicon.ico" size="16x16" type="image/x-icon">
        <link rel="stylesheet" href="CSS/homePage.css"/>
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
                        <li><a href="Login">Đăng nhập/Đăng ký</a></li>
                    </ul>
                </div>
            </div>
        </header>
        
        
            <form action="Login" method="post" name="myForm">
                <div class="login-box">
                    <input type="text" name="user" placeholder="Tên đăng nhập"><br/>
                    <input type="password" name="pass" placeholder="Mật khẩu"><br/>
                    <input type="submit" onclick="return validate()" value="Đăng nhập">
                    <a href="" class="forgot-pwd">Quên mật khẩu?</a><br/>
                    <hr>
                    <a href="sign-in" class="sign-in">Đăng ký</a>
                </div>
            </form>
            
        
    </body>
</html>
