<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}" />
<fmt:setBundle basename="lang" />

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