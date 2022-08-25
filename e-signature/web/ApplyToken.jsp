<%-- 
    Document   : ApplyToken
    Created on : 20 Feb, 2018, 12:45:30 AM
    Author     : Akshay
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%
            session.getAttribute("fname");
            session.getAttribute("eno");
            
        %>
        
        <form action="ApplyToken" method="post">
            <table align="center">
            <tr>
                <td>Employee no. </td> <td><input type="text" readonly="readonly" name="eno"  value="${eno}" ><br></td>
            </tr>
            <tr>
                <td>Name </td> <td><input type="text" readonly="readonly" name="xname" value="${fname}"><br></td>
            </tr>
            <tr>
                <td>Organization unit </td> <td><input type="text" name="xorgu"><br></td>
            </tr>
            <tr>
                <td>Organization </td> <td><input type="text" name="xorg"><br></td>
            </tr>
            <tr>
                <td>Postal Address </td> <td><input type="text" name="xaddress"><br></td>
            </tr>
            <tr>
                <td>City </td> <td><input type="text" name="xcity"><br></td>
            </tr>
            <tr>
                <td>State</td> <td><input type="text" name="xstate"><br></td>
            </tr>
            <tr>
                <td>Pin</td> <td><input type="text" name="xpin"><br></td>
            </tr>
            <tr>
                <td>Country initial </td> <td><input type="text" name="xcountry"><br></td>
            </tr>
            <tr>
                <td>Email</td><td><input type="text" name="xemail"><br></td>
            </tr>
            <tr>
                <td>Phone</td><td> <input type="text" name="xphone"><br></td>
            </tr>
            <tr>
                <td><input type="submit" value="Submit"></td>
            </tr>
            </table>            
        </form>
        
     
        
    </body>
</html>


<%--
Name <input type="text" readonly="readonly" name="xname" value="${fname}"><br><br>
            Organization unit <input type="text" name="xorgu"><br><br>
            Organization <input type="text" name="xorg"><br><br>
            Postal Address <input type="text" name="xaddress"><br><br>
            City <input type="text" name="xcity"><br><br>
            State <input type="text" name="xstate"><br><br>
            Pin <input type="text" name="xpin"><br><br>
            Country initial <input type="text" name="xcountry"><br><br>
            Email <input type="text" name="xemail"><br><br>
            Phone <input type="text" name="xphone"><br><br><br>
            <input type="submit" value="Submit">
--%>