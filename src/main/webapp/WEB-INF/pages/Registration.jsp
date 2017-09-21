<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="lang"/>

<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title><fmt:message key="info.pageTitle"/> </title>
    <link href="../../css/bootstrap.min.css" rel="stylesheet">

    <style>


        .card-container.card {
            max-width: 400px;
            min-height: 450px;
            padding: 40px 40px;
        }

        .card {
            background-color: #EFF7EE;
            padding: 20px 25px 30px;
            margin: 0 auto 25px;
            margin-top: 50px;
            -moz-border-radius: 2px;
            -webkit-border-radius: 2px;
            border-radius: 2px;
            -moz-box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.3);
            -webkit-box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.3);
            box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.3);
        }

        .profile-img-card {
            width: 96px;
            height: 96px;
            margin: 0 auto 10px;
            display: block;
            -moz-border-radius: 50%;
            -webkit-border-radius: 50%;
            border-radius: 50%;
        }

        .profile-name-card {
            font-size: 16px;
            font-weight: bold;
            text-align: center;
            margin: 10px 0 0;
            min-height: 1em;
        }

        .reauth-email .reauth-emaill {
            display: block;
            color: #404040;
            line-height: 2;
            margin-bottom: 10px;
            font-size: 14px;
            text-align: center;
            overflow: hidden;
            text-overflow: ellipsis;
            white-space: nowrap;
            -moz-box-sizing: border-box;
            -webkit-box-sizing: border-box;
            box-sizing: border-box;
        }

        .form-signin #inputLogin,
        .form-signin #inputPassword
        .form-signin #confirmPassword
        .form-signin #confirmPasswordd
        .form-signin #inputLoginn
        .form-signin #inputPasswordd
        .form-signin #inputEmaill
        .form-signin #inputEmail{
            direction: ltr;
            height: 44px;
            font-size: 16px;
        }

        .form-signin input[type=email],
        .form-signin input[type=password],
        .form-signin input[type=text],
        .form-signin button {
            width: 100%;
            display: block;
            margin-bottom: 10px;
            z-index: 1;
            position: relative;
            -moz-box-sizing: border-box;
            -webkit-box-sizing: border-box;
            box-sizing: border-box;
        }

        .form-signin .form-control:focus {
            border-color: #3B3B3B;
            outline: 0;
            -webkit-box-shadow: inset 0 0.5px 0.5px #C9C9C9, 0 0 4px #969696;
        }

        .btn.btn-signin {
            background-color: #4FBA79;
            padding: 0px;
            font-weight: 700;
            font-size: 16px;
            height: 40px;
            -moz-border-radius: 3px;
            -webkit-border-radius: 3px;
            border-radius: 3px;
            border: none;
            -o-transition: all 0.218s;
            -moz-transition: all 0.218s;
            -webkit-transition: all 0.218s;
            transition: all 0.218s;
        }

        .btn.btn-signin:hover,
        .btn.btn-signin:active,
        .btn.btn-signin:focus {
            background-color: #1EAE68;
        }

        input::-webkit-outer-spin-button,
        input::-webkit-inner-spin-button {
            -webkit-appearance: none;
            margin: 0;
        }
    </style>

</head>


<div class="container">
    <div class="card card-container">
        <img id="profile-img" class="profile-img-card" src="../../img/user.png"/>
        <p id="profile-name" class="profile-name-card"></p>
        <c:choose>
            <c:when test="${requestScope.msg ne null}">
                <hr>
                <div class="alert alert-danger">
                    <strong><fmt:message key="info.oops"/> </strong>${requestScope.msg}
                    <fmt:message key="info.tryAgain"/>
                </div>
                <hr>
            </c:when>
        </c:choose>
        <c:choose>
            <c:when test="${requestScope.manager eq true}">
                <form class="form-signin" action="adminUsers" method="post">
                    <span id="reauth-emaill" class="reauth-email"></span>
                    <input type="text" id="inputLoginn" class="form-control" pattern="([A-Za-z0-9_]+){5,15}"
                           placeholder="  <fmt:message key="field.login"/>" required autofocus name="login">
                    <input type="email" id="inputEmaill" class="form-control"
                           placeholder="  <fmt:message key="table.user.email"/>" required name="email">
                    <input type="password" pattern="([A-Za-z0-9_]+){5,15}" id="inputPasswordd" class="form-control"
                           placeholder="  <fmt:message key="field.reg.password"/>" required name="password">
                    <input type="password" pattern="([A-Za-z0-9_]+){5,15}" id="confirmPasswordd" class="form-control"
                           placeholder="  <fmt:message key="field.confirmPassword"/>" required name="confirmPassword">
                    <hr>
                    <button class="btn btn-lg btn-primary btn-block btn-signin" type="submit"
                            name="action" value="register"><fmt:message key="button.register"/>
                    </button>
                </form>
                <form class="form-signin" action="adminUsers" method="get">
                    <button class="btn btn-lg btn-primary btn-block btn-signin" type="submit"
                            name="action" value="back"><fmt:message key="button.back"/>
                    </button>
                </form>
            </c:when>
            <c:otherwise>
                <form class="form-signin" action="login" method="post">
                    <span id="reauth-email" class="reauth-email"></span>
                    <input type="text" id="inputLogin" class="form-control" pattern="([A-Za-z0-9_]+){5,15}"
                           placeholder="  <fmt:message key="field.login"/>" required autofocus name="login">
                    <input type="email" id="inputEmail" class="form-control"
                           placeholder="  <fmt:message key="table.user.email"/>" required name="email">
                    <input type="password" pattern="([A-Za-z0-9_]+){5,15}" id="inputPassword" class="form-control"
                           placeholder="  <fmt:message key="field.reg.password"/>" required name="password">
                    <input type="password" pattern="([A-Za-z0-9_]+){5,15}" id="confirmPassword" class="form-control"
                           placeholder="  <fmt:message key="field.confirmPassword"/>" required name="confirmPassword">
                    <hr>
                    <button class="btn btn-lg btn-primary btn-block btn-signin" type="submit"
                            name="action" value="register"><fmt:message key="button.register"/>
                    </button>
                </form>
                <form class="form-signin" action="login" method="get">
                    <button class="btn btn-lg btn-primary btn-block btn-signin" type="submit"
                            name="action" value="home"><fmt:message key="button.mainPage"/>
                    </button>
                </form>
            </c:otherwise>
        </c:choose>

    </div>
</div>

</html>
