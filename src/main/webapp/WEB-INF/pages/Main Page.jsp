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

        /* Layout */

        #container {
            padding-left: 200px;
            padding-right: 190px;
        }

        #container .column {
            position: relative;
            float: left;
        }

        #center {
            padding: 10px 20px;
            width: 100%;
        }

        #left {
            width: 190px;
            padding: 0 10px;
            right: 200px;
        }

        #right {
            width: 180px;
            margin-right: -100%;
            margin-left: -15%;
        }

        #footer {
            clear: both;
        }

        /* IE hack */
        * html #left {
            left: 150px;
        }

        /* Make the columns the same height as each other */
        #container {
            overflow: hidden;
        }

        #container .column {
            padding-bottom: 1001em;
            margin-bottom: -1000em;
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

        #left {
            background: #DAE9BC;
        }

        #center {
            background: #fff;
        }

        #container .column {
            padding-top: 1em;
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

        input::-webkit-outer-spin-button,
        input::-webkit-inner-spin-button {
            -webkit-appearance: none;
            margin: 0;
        }

        .dropdown-menu {
            max-width: 25px;
        }


    </style>

    <script type="text/javascript" src="js/calendar.js"></script>

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
        <c:choose>
            <c:when test="${sessionScope.user eq null}">
                <div align="right" style="margin-right: 3%">
                    <form action="login" method="get">
                        <button type="submit" class="btn btn-success" name="action" value="login">
                            <fmt:message key="button.logIn"/></button>
                        <button type="submit" class="btn btn-success" name="action" value="register">
                            <fmt:message key="button.register"/></button>
                    </form>
                </div>
            </c:when>
            <c:otherwise>
                <div align="right" style="margin-right: 3%">
                    <form action="login" method="post">
                        <fmt:message key="info.signInInfoForUser"/> ${sessionScope.user.login}
                        <button type="submit" class="btn btn-success" name="action" value="logout"
                                style="background-color:#3B3B3B;border-color:#3B3B3B;"><fmt:message
                                key="button.logOut"/>
                        </button>
                    </form>
                </div>
                <c:choose>
                    <c:when test="${sessionScope.user.role eq 'ADMIN'}">
                        <div align="right" style="margin-right: 3%">
                            <form action="admin" method="get">
                                <button type="submit" class="btn btn-success" name="action"
                                        value="cars"><fmt:message key="button.admin.cars"/>
                                </button>
                                <button type="submit" class="btn btn-success" name="action"
                                        value="users"><fmt:message key="button.admin.users"/>
                                </button>
                                <button type="submit" class="btn btn-success" name="action"
                                        value="managers"><fmt:message key="button.admin.managers"/>
                                </button>
                            </form>
                        </div>
                    </c:when>
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
            </c:otherwise>
        </c:choose>

    </div>
</header>
<div id="container">
    <main id="center" class="column">
        <article>
            <h1><fmt:message key="info.welcome"/></h1>
            <hr>
        </article>
    </main>

    <nav id="left" class="column">
        <h5><fmt:message key="info.cars.classes"/></h5>
        <ul>
            <form action="MainPageServlet" method="get">
                <c:forEach items="${requestScope.classes}" var="carClass">
                    <li>
                        <button type="submit" class="btn-link" name="carClass"
                                value="${carClass}">${carClass}
                        </button>
                    </li>
                </c:forEach>
            </form>
        </ul>
        <h5><fmt:message key="info.cars.models"/></h5>
        <ul>
            <form action="MainPageServlet" , method="get">
                <c:forEach items="${requestScope.models}" var="carModel">
                    <li>
                        <button type="submit" class="btn-link" name="model" value="${carModel}">
                                ${carModel}</button>
                    </li>
                </c:forEach>
            </form>
        </ul>
        <br>
        <h5><fmt:message key="info.cars.price"/></h5>
        <form action="MainPageServlet" method="get">
            <table>
                <tr>
                    <td><fmt:message key="info.cars.priceFrom"/>
                        <input type="number" class="form-control" name="minPrice">
                    </td>
                    <td><fmt:message key="info.cars.priceTo"/>
                        <input type="number" class="form-control" name="maxPrice">
                    </td>
                </tr>
            </table>
            <br>
            <button style="width: 100%; font-size: 1.1em;" type="submit" class="btn btn-success">
                <fmt:message key="button.cars.priceFilter"/></button>
            <hr>
            <br>
            <button style="width: 100%;" type="submit" class="btn btn-success" name="clear" value="clear">
                <fmt:message key="button.cars.clearFilters"/>
            </button>
        </form>
    </nav>
    <div id="right" class="column">
        <form action="MainPageServlet" method="get">
            <table>
                <tr>
                    <td style="white-space: pre;"><fmt:message key="info.cars.sort"/></td>
                    <td>
                        <select name="sort">
                            <option selected value="default">
                                <fmt:message key="info.cars.sort.default"/></option>
                            <option value="min-max">
                                <fmt:message key="info.cars.sort.cheapFirst"/></option>
                            <option value="max-min">
                                <fmt:message key="info.cars.sort.expensiveFirst"/></option>
                            <option value="alph">
                                <fmt:message key="info.cars.sort.alphabetically"/></option>
                        </select>
                    </td>
                    <td>
                        <button class="btn-success" type="submit" value="send"/>
                        <fmt:message key="button.cars.sort"/>
                    </td>
                </tr>
            </table>
        </form>

        <table class="table">
            <form action="order" method="get">
                <thead class="thead-inverse">
                <tr>
                    <th></th>
                    <th><fmt:message key="table.cars.class"/></th>
                    <th style="white-space: pre;"><fmt:message key="table.cars.fullName"/></th>
                    <th style="white-space: pre;"><fmt:message key="table.cars.price"/></th>
                    <th style="white-space: pre;"><fmt:message key="table.cars.driverPrice"/></th>
                    <th><fmt:message key="table.cars.status"/></th>
                    <th style="white-space: pre;"><fmt:message key="table.cars.description"/></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${requestScope.cars}" var="current">
                    <tr>
                        <th><img src="../../img/car.jpg" style="width: 200px; height: 124px"></th>
                        <td><c:out value="${current.carClassName}"/></td>
                        <td><c:out value="${current.fullName}"/></td>
                        <td><c:out value="${current.price}"/></td>
                        <td><c:out value="${current.driverPrice}"/></td>
                        <td><c:out value="${current.status}"/></td>
                        <td>
                            <div class="dropdown">
                                <span><fmt:message key="table.cars.viewDescription"/></span>
                                <div class="dropdown-content">
                                    <p>${current.description}</p>
                                </div>
                            </div>
                        </td>


                        <c:choose>
                            <c:when test="${sessionScope.user ne null}">
                                <c:choose>
                                    <c:when test="${sessionScope.msg eq 'OK'}">
                                        <td>
                                            <button class="btn btn-success" type="submit" name="action"
                                                    value="order_${current.id}">
                                                <fmt:message key="button.order"/>
                                            </button>
                                        </td>
                                    </c:when>
                                    <c:otherwise>
                                        <td>
                                            <div class="dropdown">
                                                <button class="btn btn-success" type="submit" disabled
                                                        name="action" value="${current.id}">
                                                    <fmt:message key="button.order"/>
                                                </button>
                                                <div class="dropdown-content">
                                                    <p><fmt:message key="button.order.message"/>
                                                        (${sessionScope.msg}).</p>
                                                </div>
                                            </div>
                                        </td>
                                    </c:otherwise>
                                </c:choose>

                            </c:when>
                            <c:otherwise>
                                <td>
                                    <div class="dropdown">
                                        <button class="btn btn-success" type="submit" disabled
                                                name="action" value="${current.id}">
                                            <fmt:message key="button.order"/></button>
                                        <div class="dropdown-content">
                                            <p><fmt:message key="button.order.loginMessage"/></p>
                                        </div>
                                    </div>
                                </td>
                            </c:otherwise>
                        </c:choose>
                    </tr>
                </c:forEach>
                </tbody>
            </form>
        </table>
    </div>
</div>
<div id="footer-wrapper" align="center">
    <jsp:include page="fragments/Footer.jsp"></jsp:include>
</div>
</body>
</html>