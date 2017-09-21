<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}" />
<fmt:setBundle basename="lang" />

<div  align="right" style="margin-right: 3%">
    <form action="managerServlet" method="get">
        <button type="submit" class="btn btn-success" name="action"
                value="orders"><fmt:message key="button.manager.orders"/>
        </button>
        <button type="submit" class="btn btn-success" name="action"
                value="checks"><fmt:message key="button.manager.checks"/>
        </button>
    </form>
</div>