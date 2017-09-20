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

        /* Make the columns the same height as each other */
        #container {
            overflow: hidden;
        }

        /* Fix for the footer */
        * html body {
            overflow: hidden;
        }

        * html {
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

    <div id="right" class="column">

        <table class="table">
            <thead class="thead-inverse">
            <tr>
                <th></th>
                <th>Login</th>
                <th style="white-space: pre;">Role</th>
                <th style="white-space: pre;">Email</th>
                <th style="white-space: pre;">Status</th>
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
                            <td>Blocked</td>
                        </c:when>
                        <c:otherwise>
                            <td>Active</td>
                        </c:otherwise>
                    </c:choose>

                    <td align="center">
                        <form action="adminUsers" method="post">
                            <c:choose>
                                <c:when test="${current.blocked eq true}">
                                    <button class="btn btn-success" type="submit" name="action"
                                            value="userUnblock_${current.id}"> Unblock
                                    </button>
                                </c:when>
                                <c:otherwise>
                                    <button class="btn btn-success" type="submit" name="action"
                                            value="userBlock_${current.id}"> Block
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