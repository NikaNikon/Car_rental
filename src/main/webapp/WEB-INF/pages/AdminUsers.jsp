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

        /* Make the columns the same height as each other */
        #container {
            overflow: hidden;
        }

        /* Fix for the footer */
        * html body {
            overflow: hidden;
        }

        #footer {
            clear: both;
        }

        * html #footer-wrapper{
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

        #center {
            background: #fff;
        }

        .btn-link {
            font-weight: 400;
            color: #1d1e1f;
            border-radius: 0
        }

        #footer {
            font-size: small;
            padding: 0.3em;
            background: #BCCE98;
        }

        table{
            width: 100%;
        }

        th{
            width: 16%;
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
            <form action="login" method="post">
                <fmt:message key="info.signInInfoForUser"/>${sessionScope.user.login}
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
            <h1><fmt:message key="button.admin.users"/></h1>
        </article>
    </main>

    <div id="right" class="column">

        <table class="table">
            <thead class="thead-inverse">
            <tr>
                <th></th>
                <th><fmt:message key="table.user.login"/> </th>
                <th style="white-space: pre;"><fmt:message key="table.user.role"/></th>
                <th style="white-space: pre;"><fmt:message key="table.user.email"/></th>
                <th style="white-space: pre;"><fmt:message key="table.user.status"/></th>
                <th></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${requestScope.users}" var="current">
                <tr>
                    <th><img src="../../img/user.png" style="width:80px; height:80px"></th>
                    <td><c:out value="${current.login}"/></td>
                    <td><c:out value="${current.role}"/></td>
                    <td><c:out value="${current.email}"/></td>
                    <c:choose>
                        <c:when test="${current.blocked eq true}">
                            <td><fmt:message key="table.user.blocked"/></td>
                        </c:when>
                        <c:otherwise>
                            <td><fmt:message key="table.user.active"/></td>
                        </c:otherwise>
                    </c:choose>

                    <td align="center">
                        <form action="adminUsers" method="post">
                            <c:choose>
                                <c:when test="${current.blocked eq true}">
                                    <button class="btn btn-success" type="submit" name="action"
                                            value="userUnblock_${current.id}">
                                        <fmt:message key="button.user.unblock"/>
                                    </button>
                                </c:when>
                                <c:otherwise>
                                    <button class="btn btn-success" type="submit" name="action"
                                            value="userBlock_${current.id}">
                                        <fmt:message key="button.user.block"/>
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