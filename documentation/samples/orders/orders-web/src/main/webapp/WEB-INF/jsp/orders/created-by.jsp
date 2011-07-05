<%@taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: marius
  Date: Jul 23, 2009
  Time: 12:29:20 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head><title>Simple jsp page</title></head>
<body>Place your content here</body>
<c:forEach items="${orders}" var="order">
    <c:out value="${order.name}"/> created by <c:out value="${order.createdBy.id}"/><p/>
</c:forEach>
</html>