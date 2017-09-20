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

    <script type="text/javascript">
        function ensure() {
            return confirm("Are you sure?");
        }
    </script>

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
        <div style="margin-left: 75%">
            <form action="admin" method="get">
                <button type="submit" class="btn btn-success" name="action"
                        value="cars">Cars
                </button>
                <button type="submit" class="btn btn-success" name="action"
                        value="users">Users
                </button>
                <button type="submit" class="btn btn-success" name="action"
                        value="managers">Managers
                </button>
            </form>
        </div>

    </div>
</header>
<div id="container">
    <main id="center" class="column">
        <article>
            <h1></h1>
        </article>
    </main>

    <div>
        <form action="allCars" method="get">
            <button class="btn btn-success" type="submit" name="action"
                    value="newCar"> + Add a car
            </button>
        </form>
    </div>

    <div id="right" class="column">

        <table class="table">
            <thead class="thead-inverse">
            <tr>
                <th></th>
                <th>Class</th>
                <th style="white-space: pre;">Full name</th>
                <th style="white-space: pre;">Price</th>
                <th style="white-space: pre;">Driver price</th>
                <th>Status</th>
                <th style="white-space: pre;">Description</th>
                <th></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${requestScope.cars}" var="current">
                <tr>
                    <td><img src="../../img/car.jpg" style="width:280px;height:173px"></td>
                    <td><c:out value="${current.carClassName}"/></td>
                    <td><c:out value="${current.fullName}"/></td>
                    <td><c:out value="${current.price}"/></td>
                    <td><c:out value="${current.driverPrice}"/></td>
                    <td><c:out value="${current.status}"/></td>
                    <td>
                        <div class="dropdown">
                            <span>View description</span>
                            <div class="dropdown-content">
                                <p>${current.description}</p>
                            </div>
                        </div>
                    </td>

                    <td align="center">
                        <form action="allCars" method="get">
                            <button class="btn btn-success" type="submit" name="action"
                                    value="edit_${current.id}"> Edit
                            </button>
                        </form>
                        <br><br>
                        <form onsubmit="ensure();" action="allCars" method="post">
                            <c:choose>
                                <c:when test="${current.status eq 'IN_RENT'}">
                                    <button class="btn btn-success" type="submit" name="action"
                                            value="delete_${current.id}" disabled> Delete
                                    </button>
                                </c:when>
                                <c:otherwise>
                                    <button class="btn btn-success" type="submit" name="action"
                                            value="delete_${current.id}"> Delete
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
    <jsp:include page="Footer.jsp"></jsp:include>
</div>
</body>
</html>