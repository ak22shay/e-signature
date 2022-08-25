<%-- 
    Document   : Work
    Created on : Jan 26, 2018, 1:08:13 AM
    Author     : rohit
--%>



<%@page import="java.io.File"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>WORK</title>
        <link rel="stylesheet" type="text/css" href="css/style.css"></link>
        <link rel="stylesheet" type="text/css" href="css/work.css"></link>
        
        <script>
            <%--history.forward();
            function apply(){
                File keyStoreFile = new File("C:/Akshay/My Work/final year/DigitalSign/keystore/user/24196.jks");
                if(keyStoreFile!==null){
                    //alert("already has a softtoken");
                    window.location.href = "ApplyToken.jsp";
                }
                else{
                    window.location.href = "ApplyToken.jsp";
                }
            }--%>
        </script>
    </head>
    <style>
        html { 
            margin: 0; 
            padding: 0;
            background: url("css/Keyboard.jpg") no-repeat center center fixed; 
            webkit-background-size: cover; 
            moz-background-size: cover; 
            o-background-size: cover; 
            background-size: cover; 
        }

        body {margin: 0 auto;}

        .topnav {
            overflow: hidden;
            background-color: #333;
        }

        .topnav a {
            float: left;
            color: #f2f2f2;
            text-align: center;
            padding: 14px 16px;
            text-decoration: none;
            font-size: 17px;
        }

        .topnav a:hover {
            background-color: #ddd;
            color: black;
        }

        .topnav a.active {
            background-color: #4CAF50;
            color: white;
        }

    </style>
    <body>
        <%
            response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); //(HTTP 1.1) for No Back Button afetr Logout
            response.setHeader("Pragma", "no-cache"); //(HTTP 1.0)
            response.setHeader("Expires", "0"); // (Proxies)
            if (session.getAttribute("fname") == null) {
                response.sendRedirect("Login.jsp");
            }
            
            
            
        %>
        <div class="topnav">
            <form action="Logout">
                <a class="active" href="#home">Home</a>
                <a href="#news">News</a>
                <a href="#contact">Contact</a>
                <a href="#about">About</a>                 
                <%--<a>Welcome ${fname}</a>               --%>
                <a>Welcome ${fname}</a>    
                <input type="submit" value="Logout" class="btn-logout">
            </form>   
        </div>        
                
        <div id="sidebar">
            <ul>
                <%-- <li><a href="ApplyToken.jsp" onclick="window.open('ApplyToken.jsp','newwindow','width=600,height=750'); 
return false;">Apply For Soft Token</a></li> --%>
                
                <li><a href="SoftToken.jsp">Apply For Soft Token</a></li>
                <%--<li><a href="#">Sign Text Data</a></li>--%>
                <li><a href="UploadForSign.jsp">Upload a File for Signing</a></li>
                <%--<li><a href="ViewCertificate.java">View Certificates</a></li>--%>
                <%-- <li><form action="ViewCertificate" method="post"><input type="submit" value="View Certificates" background="none" border="none" padding="0" text-decoration="none"></form></li>--%>
                <li><a href="ViewCertificate.jsp">View Certificates</a></li>
                <%--<li><form action="ViewCertificate" method="post"><input type="submit" value="download Certificate"></form></li>--%>
                <%--<li><form action="ViewSignDoc" method="post"><input type="submit" value="View Signed Documents"></form></li>--%>
                <li><a href="ViewSignedDoc.jsp">View Signed Docs</a></li>
                <li><a href="BlockToken.jsp">Block Token</a></li>
                <%--<li><form action="BlockToken" method="post"><input type="submit" value="delete token"></form></li>--%>
                <%-- <li><form ><input type="submit" value="View Signed Documents" onclick="fun()"></form></li>--%>
                
            </ul>
        </div>   
                <script>
                function fun1(){
                    alert("loading...");  
                }
                </script>
        
                
        <center>            
        <h1>Upload File for Signature</h1>
        <form method="post" action="UploadForSign" <%--enctype="multipart/form-data"--%>>
            <table border="0">
                <%--<tr>
                    <td>name the output file </td>
                    <td><input type="text" name="filename" size="50"/></td>
                </tr>--%>
               
                <tr>
                    <td>File to be Uploaded: </td>
                    <td><input type="file" name="file" size="50"/></td>
                </tr>
                <tr>
                    <td colspan="2">
                        <input type="submit" value="    Sign    ">
                    </td>
                </tr>
            </table>
        </form>
        </center>       
                
        <%--  
        <form action="FileUpload" method="post" enctype="multipart/form-data">
            <p>Select the file to be Uploaded for Signing</p>
            <input type="text" name="name">
            <input type="file" name="file"/>
            <input type="submit" value="Upload File">
        </form> --%>
        
        
    </body>
</html>
