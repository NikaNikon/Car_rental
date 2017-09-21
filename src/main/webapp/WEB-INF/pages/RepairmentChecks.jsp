<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="lang"/>

<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title><fmt:message key="info.pageTitle"/></title>
    <link href="../../css/bootstrap.min.css" rel="stylesheet">
    <script src="../../js/jquery-3.2.1.min.js"></script>
    <script src="../../js/bootstrap.min.js"></script>
    <style type="text/css">
        #container {
            padding-left: 40px;
            padding-right: 40px;
            width: 100%;
        }

        #center {
            padding: 30px 20px;
            width: 100%;
        }

        #footer {
            clear: both;
        }

        /* Make the columns the same height as each other */
        #container {
            overflow: hidden;
        }

        /* Fix for the footer */
        * html body {
            overflow: hidden;
        }

        * html #footer-wrapper {
            float: left;
            position: relative;
            width: 100%;
            padding-bottom: 10010px;
            margin-bottom: -10000px;
            background: #fff;
        }

        /* Aesthetics */
        body {
            margin: 0;
            padding: 0;
            font-family: Sans-serif;
            line-height: 1.5em;
        }

        p {
            color: #555;
        }

        nav ul {
            list-style-type: none;
            margin: 0;
            padding: 0;
        }

        nav ul a {
            color: darkgreen;
            text-decoration: none;
        }

        #header {
            font-size: large;
            padding: 0.15em;
            background: #BCCE98;
        }

        #footer {
            font-size: small;
            padding: 0.3em;
            background: #BCCE98;
        }

        #center {
            background: #fff;
        }

        .dropdown {
            position: relative;
            display: inline-block;
        }

        .dropdown-content {
            display: none;
            position: absolute;
            background-color: #f9f9f9;
            min-width: 160px;
            box-shadow: 0px 8px 16px 0px rgba(0, 0, 0, 0.2);
            padding: 12px 16px;
            z-index: 1;
        }

        .dropdown:hover .dropdown-content {
            display: block;
        }

        .btn-link {
            font-weight: 400;
            color: #1d1e1f;
            border-radius: 0
        }

    </style>

</head>
<body>
<header id="header">
    <div style="display: inline-block">
        <form action="MainPageServlet" method="get">
            <button class="btn-link"><fmt:message key="companyName"/></button>
        </form>
    </div>

    <div style="display: inline-block;" class="dropdown">
        <a data-toggle="dropdown" class="dropdown-toggle">
            <img src="../../img/langIcon.png">
            <b class="caret"></b>
        </a>
        <ul class="dropdown-menu " style="width: 25px">
            <li>
                <a href="lang?locale=en"><img src="../../img/langIcon_eng.png">
                    <fmt:message key="lang.en"/>
                </a></li>
            <li>
                <a href="lang?locale=ru"><img src="../../img/langIcon_ru.png">
                    <fmt:message key="lang.ru"/>
                </a></li>
        </ul>
    </div>
    <div>
        <div style="margin-left: 75%">
            <form action="login" method="post"><fmt:message key="info.signInInfoForUser"/>
                ${sessionScope.user.login}
                <button type="submit" class="btn btn-success" name="action" value="logout"
                        style="background-color:#3B3B3B;border-color:#3B3B3B;"><fmt:message key="button.logOut"/>
                </button>
            </form>
        </div>
        <c:choose>
            <c:when test="${sessionScope.user.role eq 'MANAGER'}">
                <div  align="right" style="margin-right: 3%">
                    <form action="managerServlet" method="get">
                        <button type="submit" class="btn btn-success" name="action"
                                value="orders"><fmt:message key="button.manager.orders"/>
                        </button>
                        <button type="submit" class="btn btn-success" name="action"
                                value="checks"><fmt:message key="button.manager.checks"/>
                        </button>
                    </form>
                </div>
            </c:when>
            <c:otherwise>
                <div  align="right" style="margin-right: 3%">
                    <form action="user" method="get">
                        <button type="submit" class="btn btn-success" name="action"
                                value="orders"><fmt:message key="button.user.orders"/>
                        </button>
                        <button type="submit" class="btn btn-success" name="action"
                                value="checks"><fmt:message key="button.user.checks"/>
                        </button>
                        <button type="submit" class="btn btn-success" name="action"
                                value="personalPage"><fmt:message key="button.user.personalPage"/>
                        </button>
                    </form>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</header>
<div id="container">
    <main id="center" class="column">
        <article>
            <h1><fmt:message key="button.user.checks"/></h1>
        </article>
    </main>
    <div>
        <form action="check" method="get">
            <button class="btn btn-success" type="submit" name="action"
                    value="unpayed"><fmt:message key="info.checks.unpayed"/>
            </button>
            <button class="btn btn-success" type="submit" name="action"
                    value="all"> <fmt:message key="button.orders.all"/>
            </button>
        </form>
    </div>
    <c:choose>
    <c:when test="${sessionScope.user.role eq 'MANAGER'}">
    <c:choose>
    <c:when test="${requestScope.checks eq null}">
        <div id="right" class="column">
            <div class="alert alert-info"><fmt:message key="info.noChecks"/></div>
        </div>
    </c:when>
    <c:otherwise>
    <div id="right" class="column">

        <table style="width: 100%" class="table">
            <thead class="thead-inverse">
            <tr>
                <th style="width: 11%"></th>
                <th style="width: 11%"><fmt:message key="info.checks.checkId"/></th>
                <th style="width: 11%"><fmt:message key="info.checks.orderId"/></th>
                <th style="width: 11%"><fmt:message key="info.checks.userId"/></th>
                <th style="width: 11%"><fmt:message key="info.checks.carId"/></th>
                <th style="width: 11%"><fmt:message key="info.checks.date"/></th>
                <th style="width: 11%"><fmt:message key="check.price"/></th>
                <th style="width: 11%"><fmt:message key="check.comment"/></th>
                <th style="width: 11%"><fmt:message key="table.cars.status"/></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${requestScope.checks}" var="check">
                <tr>
                    <td><img src="../../img/order.png"></td>
                    <td>${check.id}</td>
                    <td>${check.orderId}</td>
                    <td>${check.userId}</td>
                    <td>${check.carId}</td>
                    <td>${check.date}</td>
                    <td>${check.price}</td>
                    <td>${check.comment}</td>
                    <td>${check.status}</td>
                    <td align="center">
                </tr>
            </c:forEach>
            </tbody>
        </table>
        </c:otherwise>
        </c:choose>
        </c:when>
        <c:when test="${sessionScope.user.role eq 'CUSTOMER'}">
            <c:choose>
                <c:when test="${requestScope.checks eq null}">
                    <div id="right" class="column">
                        <div class="alert alert-info"><fmt:message key="info.noChecks"/></div>
                    </div>
                </c:when>
                <c:otherwise>
                    <div id="right" class="column">
                        <table  style="width: 100%" class="table">
                            <thead class="thead-inverse">
                            <tr>
                                <th style="width: 10%"></th>
                                <th style="width: 10%"><fmt:message key="info.checks.checkId"/></th>
                                <th style="width: 10%"><fmt:message key="info.checks.orderId"/></th>
                                <th style="width: 10%"><fmt:message key="info.checks.userId"/></th>
                                <th style="width: 10%"><fmt:message key="info.checks.carId"/></th>
                                <th style="width: 10%"><fmt:message key="info.checks.date"/></th>
                                <th style="width: 10%"><fmt:message key="check.price"/></th>
                                <th style="width: 10%"><fmt:message key="check.comment"/></th>
                                <th style="width: 10%"><fmt:message key="table.cars.status"/></th>
                                <th style="width: 10%"></th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${requestScope.checks}" var="check">
                                <tr>
                                    <td><img src="../../img/order.png"></td>
                                    <td>${check.id}</td>
                                    <td>${check.orderId}</td>
                                    <td>${check.userId}</td>
                                    <td>${check.carId}</td>
                                    <td>${check.date}</td>
                                    <td>${check.price}</td>
                                    <td>${check.comment}</td>
                                    <td>${check.status}</td>
                                    <td align="center">
                                    <c:choose>
                                        <c:when test="${check.status eq 'UNPAYED'}">
                                            <form action="check" method="post">
                                                <button class="btn btn-success" type="submit"
                                                        name="action" value="pay_${check.id}">
                                                    <fmt:message key="button.check.pay"/>
                                                </button>
                                            </form>
                                        </c:when>
                                    </c:choose>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </c:otherwise>
            </c:choose>
        </c:when>
        </c:choose>
    </div>
</div>
<div id="footer-wrapper" align="center">
    <jsp:include page="fragments/Footer.jsp"></jsp:include>
</div>
</body>
</html>