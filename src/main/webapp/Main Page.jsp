<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>

<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Car rental</title>
    <link href="css/bootstrap.min.css" rel="stylesheet">
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
            padding: 0.3em;
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

    </style>
</head>
<body>
<header id="header">
    <p>The best car rental company</p>
    <div style="margin-left: 85%">
        <c:choose>
            <c:when test="${sessionScope.user eq null}">
                <form action="login" method="get">
                    <button type="submit" class="btn btn-success" name="login" value="login">Log in</button>
                    <button type="submit" class="btn btn-success" name="login" value="register">Register</button>
                </form>
            </c:when>
            <c:otherwise>
                <p>You signed in as ${sessionScope.user.login}</p>
                <form action="login" method="post">
                    <button type="submit" class="btn btn-success" name="action"
                            value="logout" style="align:center;">Log out
                    </button>
                </form>
            </c:otherwise>
        </c:choose>

    </div>
</header>
<div id="container">
    <main id="center" class="column">
        <article>
            <h1>Welcome to our cool site!</h1>
        </article>
    </main>

    <nav id="left" class="column">
        <h5>Classes:</h5>
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
        <h5>Models:</h5>
        <ul>
            <form action="MainPageServlet" , method="get">
                <li>
                    <button type="submit" class="btn-link" name="model" value="Peugeot">Peugeot</button>
                </li>
                <li>
                    <button type="submit" class="btn-link" name="model" value="Daewoo">Daewoo</button>
                </li>
                <li>
                    <button type="submit" class="btn-link" name="model" value="Infiniti">Infiniti</button>
                </li>
                <li>
                    <button type="submit" class="btn-link" name="model" value="Mercedes">Mercedes</button>
                </li>
            </form>
        </ul>
        <br>
        <h5>Price: </h5>
        <form action="MainPageServlet" method="get">
            <table>
                <tr>
                    <td>From
                        <input type="number" class="form-control" name="minPrice">
                    </td>
                    <td>to
                        <input type="number" class="form-control" name="maxPrice">
                    </td>
                </tr>
            </table>
            <br>
            <button style="width: 100%; font-size: 1.1em;" type="submit" class="btn btn-success">Submit</button>
            <hr>
            <br>
            <button style="width: 100%;" type="submit" class="btn btn-success" name="clear" value="clear">
                Clear all filters
            </button>
        </form>
    </nav>
    <div id="right" class="column">
        <form action="MainPageServlet" method="get">
            <table>
                <tr>
                    <td style="white-space: pre;">Sort by:</td>
                    <td>
                        <select name="sort">
                            <option selected value="default">default</option>
                            <option value="min-max">cheap first</option>
                            <option value="max-min">expensive first</option>
                            <option value="alph">alphabetically</option>
                        </select>
                    </td>
                    <td>
                        <button class="btn-success" type="submit" value="send"/>
                        submit
                    </td>
                </tr>
            </table>
        </form>

        <table class="table">
            <form action="MainPageServlet" method="get">
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
                        <th><img src="car.jpg" style="width:280px;height:173px"></th>
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
                        <td>
                            <button class="btn btn-success" type="submit" id="${current.id}"> Order</button>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </form>
        </table>
    </div>
</div>
<div id="footer-wrapper" align="center">
    <footer id="footer"><p>Thanks for watching</p>
        <p>Contacts:
            <br>07nikon@gmail.com
            <br>+380990372226
        </p>
    </footer>
</div>
</body>
</html>