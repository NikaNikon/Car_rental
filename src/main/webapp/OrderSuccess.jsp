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
            margin-top: 150px;
        }

        .pgbg{
            background-color: #F7F7F7;
        }

    </style>

</head>
<body>
<div class="pgbg">
    <div class="container">
        <div class="row text-center">
            <div class="col-sm-6 col-sm-offset-3">
                <h2 style="color:#0fad00"><fmt:message key="successOrderMsg"/> </h2>
                <img src="img/orderSuccess.png"><br><br>
                <a href="/MainPageServlet" class="btn btn-success"><fmt:message key="button.mainPage"/> </a>
                <br><br>
            </div>

        </div>
    </div>
</div>
</body>
</html>