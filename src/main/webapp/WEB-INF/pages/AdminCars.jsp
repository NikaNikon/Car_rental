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

    <script type="text/javascript">
        function ensure() {
            return confirm("Are you sure?");
        }
    </script>

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
            <form action="login" method="post">
                <fmt:message key="info.signInInfoForUser"/> ${sessionScope.user.login}
                <button type="submit" class="btn btn-success" name="action" value="logout"
                        style="background-color:#3B3B3B;border-color:#3B3B3B;"><fmt:message key="button.logOut"/>
                </button>
            </form>
        </div>
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
    </div>
</header>
<div id="container">
    <main id="center" class="column">
        <article>
            <h1><fmt:message key="button.admin.cars"/></h1>
        </article>
    </main>

    <div>
        <form action="allCars" method="get">
            <button class="btn btn-success" type="submit" name="action"
                    value="newCar"> <fmt:message key="admin.button.addCar"/>
            </button>
        </form>
    </div>

    <div id="right" class="column">

        <table class="table">
            <thead class="thead-inverse">
            <tr>
                <th></th>
                <th><fmt:message key="table.cars.licensePlate"/></th>
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
                    <td><img src="../../img/car.jpg" style="width:280px;height:173px"></td>
                    <td><c:out value="${current.licensePlate}"/></td>
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

                    <td align="center">
                        <form action="allCars" method="get">
                            <button class="btn btn-success" type="submit" name="action"
                                    value="edit_${current.id}"> <fmt:message key="button.admin.edit"/>
                            </button>
                        </form>
                        <br>
                        <form onsubmit="ensure();" action="allCars" method="post">
                            <c:choose>
                                <c:when test="${current.status eq 'IN_RENT'}">
                                    <button class="btn btn-success" type="submit" name="action"
                                            value="delete_${current.id}" disabled>
                                        <fmt:message key="button.admin.delete"/>
                                    </button>
                                </c:when>
                                <c:otherwise>
                                    <button class="btn btn-success" type="submit" name="action"
                                            value="delete_${current.id}">
                                        <fmt:message key="button.admin.delete"/>
                                    </button>
                                </c:otherwise>
                            </c:choose>
                        </form>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
<div id="footer-wrapper" align="center">
    <jsp:include page="fragments/Footer.jsp"></jsp:include>
</div>
</body>
</html>