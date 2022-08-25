<%-- 
    Document   : Login
    Created on : Jan 26, 2018, 1:07:18 AM
    Author     : rohit
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" type="text/css" href="css/style.css"></link>        
        <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css" rel="stylesheet">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>LOGIN</title>
        
        <script>
            //history.forward();
        </script>
    </head>

    <body>
        <div class="container">
            <img src="css/login.png">    
            <form action="Login" method="post"> 
                <div class="form-input">
                    <input type="text" name="eno" placeholder="Enter Employee no."><br><br>
                </div>
                <div class="form-input">
                    <input type="password" name="pass" placeholder="Enter Password"><br><br>
                </div>
                <b><input type="submit" value="LOGIN" class="btn-login" ></b><br>
            </form>

            <form action="Register.jsp" method="post">
                <p> IF NEW USER THEN REGISTER </p>
                <input type="submit" value="REGISTER" class="btn-login">
            </form>
        </div>    
    </body>
</html>
