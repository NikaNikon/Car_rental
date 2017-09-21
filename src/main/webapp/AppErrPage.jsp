<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="lang"/>

<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Car rental</title>
    <link href="css/bootstrap.min.css" rel="stylesheet">

    <style>

        .container{
            margin-left: 380px;
        }

    </style>

</head>
<body>
<div class="pgbg">
    <div class="container">
        <div class="row text-center">
            <div class="col-sm-6 col-sm-offset-3">
                <br><br> <h2 style="color:#E10909"><fmt:message key="appErrMsg"/> </h2>
                <img src="img/appErr.png">
                <br><br>
                <a href="/MainPageServlet" class="btn btn-success"><fmt:message key="button.mainPage"/></a>
            </div>

        </div>
    </div>
</div>
</body>
</html>

