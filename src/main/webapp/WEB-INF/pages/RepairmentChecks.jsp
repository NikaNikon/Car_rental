<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>

<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Car rental</title>
    <link href="../../css/bootstrap.min.css" rel="stylesheet">
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
    <form action="MainPageServlet" method="get">
        <button class="btn-link">The best car rental company</button>
        <hr>
    </form>
    <div>
        <div style="margin-left: 75%">
            <form action="login" method="post">You signed in as ${sessionScope.user.login}
                <button type="submit" class="btn btn-success" name="action" value="logout"
                        style="background-color:#3B3B3B;border-color:#3B3B3B;">Log out
                </button>
            </form>
        </div>
        <c:choose>
            <c:when test="${sessionScope.user.role eq 'MANAGER'}">
                <div style="margin-left: 78%">
                    <form action="managerServlet" method="get">
                        <button type="submit" class="btn btn-success" name="action"
                                value="orders">Orders
                        </button>
                        <button type="submit" class="btn btn-success" name="action"
                                value="checks">Repairment checks
                        </button>
                    </form>
                </div>
            </c:when>
            <c:otherwise>
                <div style="margin-left: 75%">
                    <form action="user" method="get">
                        <button type="submit" class="btn btn-success" name="action"
                                value="orders">Orders
                        </button>
                        <button type="submit" class="btn btn-success" name="action"
                                value="checks">Checks
                        </button>
                        <button type="submit" class="btn btn-success" name="action"
                                value="personalPage">Personal page
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
            <h1>Repairment checks</h1>
        </article>
    </main>
    <div>
        <form action="check" method="get">
            <button class="btn btn-success" type="submit" name="action"
                    value="unpayed"> Unpayed checks
            </button>
            <button class="btn btn-success" type="submit" name="action"
                    value="all"> All checks
            </button>
        </form>
    </div>
    <c:choose>
    <c:when test="${sessionScope.user.role eq 'MANAGER'}">
    <c:choose>
    <c:when test="${requestScope.checks eq null}">
        <div id="right" class="column">
            <div class="alert alert-info">No unpayed checks</div>
        </div>
    </c:when>
    <c:otherwise>
    <div id="right" class="column">

        <table class="table">
            <thead class="thead-inverse">
            <tr>
                <th></th>
                <th>Check id</th>
                <th>Order id</th>
                <th>User id</th>
                <th>Car id</th>
                <th>Check date</th>
                <th>Price</th>
                <th>Comment</th>
                <th>Status</th>
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
                        <div class="alert alert-info">No unpayed checks</div>
                    </div>
                </c:when>
                <c:otherwise>
                    <div id="right" class="column">
                        <table class="table">
                            <thead class="thead-inverse">
                            <tr>
                                <th></th>
                                <th>Check id</th>
                                <th>Order id</th>
                                <th>User id</th>
                                <th>Car id</th>
                                <th>Check date</th>
                                <th>Price</th>
                                <th>Comment</th>
                                <th>Status</th>
                                <th></th>
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
                                                        name="action" value="pay_${check.id}">Pay</button>
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
</body>
</html>