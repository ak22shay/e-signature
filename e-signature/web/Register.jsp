<%-- 
    Document   : Register
    Created on : Jan 26, 2018, 6:07:26 PM
    Author     : rohit
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>REGISTER</title>
        <link rel="stylesheet" type="text/css" href="css/style.css"></link>
        <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css" rel="stylesheet">
    </head>

    <body>
        <div class="container">
            <form action="Register" method="post">
                <p>REGISTRATION PAGE</p>
                <div class="form-input">
                    <input type="text" name="fname" placeholder="Enter First Name"><br><br>
                </div>
                <div class="form-input">
                    <input type="text" name="lname" placeholder="Enter Last Name"><br><br>
                </div>
                <div class="form-input">
                    <input type="text" name="ename" placeholder="Enter Employee no."><br><br>
                </div>    
                <div class="form-input">    
                    <input type="text" name="phone" placeholder="Enter Phone no."><br><br>
                </div>
                <div class="form-input">    
                    <input type="password" name="pass" placeholder="Enter Password"><br><br>
                </div>
                <input type="submit" value="REGISTER" class="btn-login">
            </form>
        </div>    
    </body>

</html>
