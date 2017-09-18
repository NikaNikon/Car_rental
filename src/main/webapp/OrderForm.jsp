<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>

<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Car rental</title>

    <link href="css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="css/pickaday.css">

    <style>


        .card-container.card {
            max-width: 570px;
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

        .reauth-email .reauth-emaill {
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

        .form-signin #inputDriver,
        .form-signin #inputName,
        .form-signin #inputLastName,
        .form-signin #inputMiddleName,
        .form-signin #inputDateOfBirth,
        .form-signin #inputPhone {
            direction: ltr;
            height: 44px;
            font-size: 16px;
        }

        .form-signin input[type=email],
        .form-signin input[type=password],
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
        <img id="profile-img" class="profile-img-card" src="user.png"/>
        <p id="profile-name" class="profile-name-card"></p>


        <form class="form-signin" action="order" method="post">
            <span id="reauth-email" class="reauth-email"></span>

            <c:choose>
                <c:when test="${sessionScope.user.passportData eq null}">
                    <label for="inputName">First name</label>
                    <input class="form-control" id="inputName" type="text" pattern="([A-Z][a-z]+)" name="name" required>

                    <label for="inputMiddleName">Middle name</label>
                    <input class="form-control" id="inputMiddleName" type="text" pattern="([A-Z][a-z]+)" name="middleName"
                           required>

                    <label for="inputLastName">Last name</label>
                    <input class="form-control" id="inputLastName" type="text" pattern="([A-Z][a-z]+)" name="lastName" required>

                    <label for="dateOfBirth">Date of birth</label>
                    <input type="date" class="form-control"  id="dateOfBirth"
                           name="dateOfBirth" max="${requestScope.maxDateOfBirth}" required>

                    <label for="inputPhone">Phone</label>
                    <input class="form-control" type="text" id="inputPhone" name="phone"
                           placeholder="+38(0 _ _) _ _ _ _ _ _ _"
                           pattern="(\+380.{9})" required>
                </c:when>
                <c:otherwise>
                    <c:set var="passport" value="${sessionScope.user.passportData}"></c:set>
                    <input class="form-control" type="text" name="name" disabled value="${passport.firstName}">
                    <input class="form-control" type="text" name="middleName" disabled
                           value="${passport.middleName}">
                    <input class="form-control" type="text" name="lastName" disabled
                           value="${passport.lastName}">
                    <input type="date" class="form-control" name="dateOfBirth" disabled value="${requestScope.birthday}">
                    <br>
                    <input class="form-control" type="text" name="phone" disabled value="${passport.phone}">
                    <div class="alert alert-info">
                        <strong>Info!</strong> If you want to change your passport data, you can do it
                        in your profile before making order.
                    </div>
                </c:otherwise>
            </c:choose>

            <hr>
            <label>Rental dates </label><br>
            <div style="display: inline-block">
                <label for="start">From:</label>
                <br>
                <input class="form-control"  style="width: 80%" type="text" id="start" name="startDate" required>
            </div>

            <div style="display: inline-block">
                <label for="end">To:</label>
                <br>
                <input class="form-control" style="width: 80%" type="text" id="end" name="endDate" required>
            </div>

            <br><br>
            <div class="checkbox">
            <label><input type="checkbox" name="driver"> Driver </label>
            </div>


            <script src="js/moment.min.js"></script>
            <script src="js/pickaday.js"></script>
            <script>

                var startDate,
                    endDate,
                    updateStartDate = function() {
                        startPicker.setStartRange(startDate);
                        endPicker.setStartRange(startDate);
                        endPicker.setMinDate(startDate);
                    },
                    updateEndDate = function() {
                        startPicker.setEndRange(endDate);
                        startPicker.setMaxDate(endDate);
                        endPicker.setEndRange(endDate);
                    },
                    startPicker = new Pikaday({
                        field: document.getElementById('start'),
                        format: 'YYYY-MM-DD',
                        minDate: new Date(new Date().getFullYear(),new Date().getMonth(),
                            new Date().getDate()+1),
                        maxDate: new Date(new Date().getFullYear(),new Date().getMonth(),
                            new Date().getDate()+3),
                        onSelect: function() {
                            startDate = this.getDate();
                            updateStartDate();
                        }
                    }),
                    endPicker = new Pikaday({
                        field: document.getElementById('end'),
                        format: 'YYYY-MM-DD',
                        minDate: new Date(),
                        maxDate: new Date(new Date().getFullYear(),new Date().getMonth()+3,
                            new Date().getDate()+3),
                        onSelect: function() {
                            endDate = this.getDate();
                            updateEndDate();
                        }
                    }),
                    _startDate = startPicker.getDate(),
                    _endDate = endPicker.getDate();
                if (_startDate) {
                    startDate = _startDate;
                    updateStartDate();
                }
                if (_endDate) {
                    endDate = _endDate;
                    updateEndDate();
                }
            </script>

            <input type="hidden" name="carId" value="${requestScope.carId}">

            <hr><br>
            <button class="btn btn-lg btn-primary btn-block btn-signin" type="submit"
                    name="action" value="makeOrder">Make order
            </button>
        </form>
        <form action="order" class="form-signin" method="get">
            <input type="hidden" name="carId" value="${requestScope.carId}">
            <button class="btn btn-lg btn-primary btn-block btn-signin" type="submit"
                    name="action" value="home">Main page
            </button>
        </form>

    </div>
</div>

</html>
