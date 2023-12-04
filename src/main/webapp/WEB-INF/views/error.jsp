<%--
  Created by IntelliJ IDEA.
  User: gimseonghyeon
  Date: 12/3/23
  Time: 3:44 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Error Page</title>
    <link rel="stylesheet" href="style.css"/>
</head>
<body>

<table>
    <tbody>
    <tr>
        <th>status_code</th>
        <td><c:out value="${status_code}"/></td>
    </tr>
    <tr>
        <th>exception_type</th>
        <td><c:out value="${exception_type}"/></td>
    </tr>
    <tr>
        <th>message</th>
        <td><c:out value="${message}"/></td>
    </tr>
    <tr>
        <th>exception</th>
        <td><c:out value="${exception}"/></td>
    </tr>
    <tr>
        <th>request_uri</th>
        <td><c:out value="${request_uri}"/></td>
    </tr>
    </tbody>

</table>
</body>
</html>