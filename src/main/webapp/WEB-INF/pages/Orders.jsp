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
            <h1>Orders</h1>
                <c:choose>
                    <c:when test="${requestScope.msg ne null}">
                        <div class="alert alert-danger">
                            <strong>WARNING!</strong> Repairment check is already registered for this order!
                        </div>
                    </c:when>
                </c:choose>
        </article>
    </main>
    <c:choose>
    <c:when test="${sessionScope.user.role eq 'MANAGER'}">
    <div>
        <form action="orders" method="get">
            <button class="btn btn-success" type="submit" name="action"
                    value="new"> New orders
            </button>
            <button class="btn btn-success" type="submit" name="action"
                    value="active"> Active orders
            </button>
            <button class="btn btn-success" type="submit" name="action"
                    value="closed"> Closed orders
            </button>
        </form>
    </div>
    <c:choose>
    <c:when test="${requestScope.orders eq null}">
        <div id="right" class="column">
            <div class="alert alert-info">No new orders</div>
        </div>
    </c:when>
    <c:otherwise>
    <div id="right" class="column">

        <table class="table">
            <thead class="thead-inverse">
            <tr>
                <th></th>
                <th>Car id</th>
                <th>User id</th>
                <th>Start date</th>
                <th>End date</th>
                <th>Order date</th>
                <th>Driver</th>
                <th>Total price</th>
                <th>Status</th>
                <th>Comment</th>
                <th></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${requestScope.orders}" var="order">
                <tr>
                    <td><img src="../../img/order.png" style="width: 120px; height: 120px"></td>
                    <td>${order.key.carId}</td>
                    <td>${order.key.userId}</td>
                    <td>${order.key.startDate}</td>
                    <td>${order.key.endDate}</td>
                    <td>${order.key.orderDate}</td>
                    <td>
                        <c:choose>
                            <c:when test="${order.key.driver eq true}">
                                YES
                            </c:when>
                            <c:otherwise>
                                NO
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td>${order.key.totalPrice}</td>
                    <td>${order.value}</td>
                    <c:choose>
                        <c:when test="${order.key.managerComment ne null}">
                            <td>${order.key.managerComment}</td>
                        </c:when>
                        <c:otherwise>
                            <td></td>
                        </c:otherwise>
                    </c:choose>
                    <td align="center">
                        <c:choose>
                            <c:when test="${order.value eq 'NEW'}">
                                <form action="orders" method="post">
                                    <button class="btn btn-success" type="submit" name="action"
                                            value="confirm_${order.key.id}"> Confirm
                                    </button>
                                    <button class="btn btn-success" type="submit" name="action"
                                            value="reject_${order.key.id}"> Reject
                                    </button>
                                </form>
                            </c:when>
                            <c:when test="${order.value eq 'CONFIRMED'}">
                                <form action="orders" method="post">
                                    <button class="btn btn-success" type="submit" name="action"
                                            value="close_${order.key.id}"> Close
                                    </button>
                                </form>
                            </c:when>
                            <c:when test="${order.value eq 'CLOSED'}">
                                        <form action="orders" method="post">
                                            <button class="btn btn-success" type="submit" name="action"
                                                    value="check_${order.key.id}"> Repairment check
                                            </button>
                                        </form>

                            </c:when>
                        </c:choose>

                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        </c:otherwise>
        </c:choose>
        </c:when>
        <c:when test="${sessionScope.user.role eq 'CUSTOMER'}">
            <div>
                <form action="orders" method="get">
                    <button class="btn btn-success" type="submit" name="action"
                            value="new"> Active orders
                    </button>
                    <button class="btn btn-success" type="submit" name="action"
                            value="all"> All orders
                    </button>
                </form>
            </div>
            <c:choose>
                <c:when test="${requestScope.orders eq null}">
                    <div id="right" class="column">
                        <div class="alert alert-info">No active orders</div>
                    </div>
                </c:when>
                <c:otherwise>
                    <div id="right" class="column">
                        <table class="table">
                            <thead class="thead-inverse">
                            <tr>
                                <th></th>
                                <th>Car id</th>
                                <th>Start date</th>
                                <th>End date</th>
                                <th>Order date</th>
                                <th>Driver</th>
                                <th>Total price</th>
                                <th>Status</th>
                                <th>Comment</th>
                                <th></th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${requestScope.orders}" var="order">
                                <tr>
                                    <td><img src="../../img/order.png" style="width: 120px; height: 120px"></td>
                                    <td>${order.key.carId}</td>
                                    <td>${order.key.startDate}</td>
                                    <td>${order.key.endDate}</td>
                                    <td>${order.key.orderDate}</td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${order.key.driver eq true}">
                                                YES
                                            </c:when>
                                            <c:otherwise>
                                                NO
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>${order.key.totalPrice}</td>
                                    <c:choose>
                                        <c:when test="${order.value eq 'NEW'}">
                                            <td> Waiting for confirmation</td>
                                        </c:when>
                                        <c:otherwise>
                                            <td>${order.value}</td>
                                        </c:otherwise>
                                    </c:choose>
                                    <c:choose>
                                        <c:when test="${order.key.managerComment ne null}">
                                            <td>${order.key.managerComment}</td>
                                        </c:when>
                                        <c:otherwise>
                                            <td></td>
                                        </c:otherwise>
                                    </c:choose>
                                    <c:choose>
                                        <c:when test="${order.value eq 'CONFIRMED'}">
                                            <td>
                                                <form action="orders" method="get">
                                                <button class="btn btn-success" type="submit"
                                                        name="action"
                                                        value="getCheck_${order.key.id}">Get a check
                                                </button>
                                                </form>
                                            </td>
                                        </c:when>
                                    </c:choose>

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