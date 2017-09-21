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

    <style>


        .card-container.card {
            max-width: 500px;
            min-height: 450px;
            padding: 40px 40px;
        }

        .card {
            background-color: #EFF7EE;
            padding: 20px 25px 30px;
            margin: 0 auto 25px;
            margin-top: 50px;
            -moz-border-radius: 2px;
            -webkit-border-radius: 2px;
            border-radius: 2px;
            -moz-box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.3);
            -webkit-box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.3);
            box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.3);
        }

        .profile-img-card {
            width: 96px;
            height: 96px;
            margin: 0 auto 10px;
            display: block;
            -moz-border-radius: 50%;
            -webkit-border-radius: 50%;
            border-radius: 50%;
        }

        .profile-name-card {
            font-size: 16px;
            font-weight: bold;
            text-align: center;
            margin: 10px 0 0;
            min-height: 1em;
        }

        .reauth-email {
            display: block;
            color: #404040;
            line-height: 2;
            margin-bottom: 10px;
            font-size: 14px;
            text-align: center;
            overflow: hidden;
            text-overflow: ellipsis;
            white-space: nowrap;
            -moz-box-sizing: border-box;
            -webkit-box-sizing: border-box;
            box-sizing: border-box;
        }

        .form-signin input[type=text],
        .form-signin button {
            width: 100%;
            display: block;
            margin-bottom: 10px;
            z-index: 1;
            position: relative;
            -moz-box-sizing: border-box;
            -webkit-box-sizing: border-box;
            box-sizing: border-box;
        }

        .form-signin .form-control:focus {
            border-color: #3B3B3B;
            outline: 0;
            -webkit-box-shadow: inset 0 0.5px 0.5px #C9C9C9, 0 0 4px #969696;
        }

        .btn.btn-signin {
            background-color: #4FBA79;
            padding: 0px;
            font-weight: 700;
            font-size: 16px;
            height: 40px;
            -moz-border-radius: 3px;
            -webkit-border-radius: 3px;
            border-radius: 3px;
            border: none;
            -o-transition: all 0.218s;
            -moz-transition: all 0.218s;
            -webkit-transition: all 0.218s;
            transition: all 0.218s;
        }

        .btn.btn-signin:hover,
        .btn.btn-signin:active,
        .btn.btn-signin:focus {
            background-color: #1EAE68;
        }

        input::-webkit-outer-spin-button,
        input::-webkit-inner-spin-button {
            -webkit-appearance: none;
            margin: 0;
        }

    </style>

</head>


<div class="container">
    <div class="card card-container">

        <img id="profile-img" class="profile-img-card" src="../../img/car.png"/>
        <p id="profile-name" class="profile-name-card"></p>
        <c:choose>
            <c:when test="${requestScope.msg ne null}">
                <hr>
                <div class="alert alert-danger">
                    <strong>Oops! </strong>${requestScope.msg} Please, try again.
                </div>
                <hr>
            </c:when>
        </c:choose>
        <form class="form-signin" action="carForm" method="post">
            <span id="reauth-email" class="reauth-email"></span>
            <c:set var="car" value="${requestScope.car}"></c:set>
            <c:choose>
                <c:when test="${car ne null}">

                    <label for="licensePlate"> <fmt:message key="table.cars.licensePlate"/> </label>
                    <input type="text" class="form-control" required name="licensePlate" id="licensePlate"
                           pattern="([A-Z]{2}[0-9]{4}[A-Z]{2})" value="${car.licensePlate}">

                    <label for="modell"> <fmt:message key="table.cars.model"/> </label>
                    <input type="text" class="form-control" pattern="([A-Za-z\s*]+)"
                           required autofocus name="model" id="modell" value="${car.model}">

                    <label for="fullNamee"> <fmt:message key="table.cars.fullName"/> </label>
                    <input type="text" class="form-control" required name="fullName" id="fullNamee"
                           value="${car.fullName}">


                    <label for="carClasss"> <fmt:message key="table.cars.class"/> </label>
                    <select class="form-control" name="carClass" required id="carClasss">
                        <c:forEach items="${requestScope.classes}" var="curClass">
                            <c:choose>
                                <c:when test="${curClass.id eq car.carClassId}">
                                    <option selected value="${curClass.id}">${curClass.carClassName}</option>
                                </c:when>
                                <c:otherwise>
                                    <option value="${curClass.id}">${curClass.carClassName}</option>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </select>

                    <br>
                    <label for="descriptionn"> <fmt:message key="table.cars.description"/> </label>
                    <textarea class="form-control" rows="6"
                              required name="description" id="descriptionn">${car.description}</textarea><br>

                    <table>
                        <tr>
                            <td>
                                <label for="pricee"> <fmt:message key="table.cars.price"/> </label>
                                <input type="number" class="form-control"
                                       required name="price" id="pricee" value="${car.price}">
                            </td>
                            <td>
                                <label for="driverPricee">
                                    <fmt:message key="table.cars.driverPrice"/> </label>
                                <input type="number" class="form-control"
                                       required name="driverPrice" id="driverPricee" value="${car.driverPrice}">
                            </td>
                        </tr>
                    </table>

                    <label for="status"> <fmt:message key="table.cars.status"/> </label>
                    <select class="form-control" name="status" required id="status">
                        <c:forEach items="${requestScope.statuses}" var="curStatus">
                            <c:choose>
                                <c:when test="${curStatus eq car.status}">
                                    <option selected value="${curStatus}">${curStatus}</option>
                                </c:when>
                                <c:otherwise>
                                    <option value="${curStatus}">${curStatus}</option>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </select>

                    <br>
                    <hr>
                    <br>
                    <button class="btn btn-lg btn-primary btn-block btn-signin" type="submit"
                            name="action" value="update"><fmt:message key="button.save"/>
                    </button>
                </c:when>
                <c:otherwise>

                    <label for="licensePlate"> <fmt:message key="table.cars.licensePlate"/> </label>
                    <input type="text" class="form-control" required name="licensePlate" id="licensePlate"
                           autofocus pattern="([A-Z]{2}[0-9]{4}[A-Z]{2})">

                    <label for="model"> <fmt:message key="table.cars.model"/> </label>
                    <input type="text" class="form-control" pattern="([A-Za-z\s*]+)"
                           required name="model" id="model">

                    <label for="fullName"> <fmt:message key="table.cars.fullName"/> </label>
                    <input type="text" class="form-control" required name="fullName" id="fullName">


                    <label for="carClass"> <fmt:message key="table.cars.class"/> </label>
                    <select class="form-control" name="carClass" required id="carClass">
                        <c:forEach items="${requestScope.classes}" var="curClass">
                            <option value="${curClass.id}">${curClass.carClassName}</option>
                        </c:forEach>
                    </select>

                    <br>
                    <label for="description"> <fmt:message key="table.cars.description"/> </label>
                    <textarea class="form-control" rows="6" required name="description"
                              id="description"> </textarea><br>

                    <table>
                        <tr>
                            <td>
                                <label for="price"> <fmt:message key="table.cars.price"/> </label>
                                <input type="number" class="form-control" required name="price" id="price">
                            </td>
                            <td>
                                <label for="driverPrice"> <fmt:message key="table.cars.driverPrice"/> </label>
                                <input type="number" class="form-control" required name="driverPrice" id="driverPrice">
                            </td>
                        </tr>
                    </table>
                    <br>
                    <hr>
                    <br>
                    <button class="btn btn-lg btn-primary btn-block btn-signin" type="submit"
                            name="action" value="save"><fmt:message key="button.save"/>
                    </button>
                </c:otherwise>
            </c:choose>
        </form>
        <form action="carForm" method="post">
            <button class="btn btn-lg btn-primary btn-block btn-signin" type="submit"
                    name="action" value="back"><fmt:message key="button.back"/>
            </button>
        </form>
    </div>
</div>

</html>

