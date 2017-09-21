<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}" />
<fmt:setBundle basename="lang" />

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