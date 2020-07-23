<%-- 
    Document   : login
    Created on : Jun 20, 2020, 2:48:01 PM
    Author     : NhatBPM;
--%>
<%@page import="dreamTravel.utilities.APIWrapper" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Login Page</title>
        <meta charset="utf-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <meta name="viewport" content="width=device-width, initial-scale=1" />
        <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
        <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
        <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
        <link href="homeStyle.css" rel="stylesheet">
        <link href="loginStyle.css" rel="stylesheet">
    </head>
    <body>
        <div>
            <nav class="navbar navbar-expand-sm navbar-dark ">
                <a class="navbar-brand display-4" href="search">DREAM TRAVEL</a>
                <div class="collapse navbar-collapse">
                    <ul class="navbar-nav ml-auto">
                        <li class="nav-item">
                            <a class="nav-link active" href="loginPage">Login</a>
                        </li>
                    </ul>
                </div>
            </nav>
        </div>
        <div class="login-form">
            <form action="login" method="post">
                <h2 class="text-center">Sign in</h2>
                <div class="form-group">
                    <c:set var="loginMsg" value="${requestScope.loginMsg}"/>
                    <c:if test="${not empty loginMsg}">
                        <p class="alert alert-danger container">${loginMsg}</p> 
                    </c:if>
                </div>
                <div class="form-group">
                    <div class="input-group">
                        <span class="input-group-addon"><i class="fa fa-user"></i></span>
                        <input type="text" class="form-control" name="txtUsername" placeholder="Username"
                               required="required" value="${param.txtUsername}"/>
                    </div>
                </div>
                <div class="form-group">
                    <div class="input-group">
                        <span class="input-group-addon"><i class="fa fa-lock"></i></span>
                        <input type="password" class="form-control" name="txtPassword" placeholder="Password"
                               required="required" value="${param.txtPassword}"/>
                    </div>
                </div>
                <div class="form-group">
                    <button type="submit" class="btn btn-dark login-btn btn-block">
                        Sign in
                    </button>
                </div>
                <div class="or-seperator"><i>or</i></div>
                <div class="text-center social-btn">
                    <p class="text-center">Login with your social media account</p>
                    <a href="${APIWrapper.getDiaLogLink()}" class="btn btn-primary"><i class="fa fa-facebook"></i>&nbsp; Facebook</a>
                </div>  
            </form>
        </div>
    </body>
</html>
