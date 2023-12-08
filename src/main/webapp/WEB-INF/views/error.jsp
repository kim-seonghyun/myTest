<%@ page contentType="text/html;charset=UTF-8" language="java" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Error Page</title>
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
</head>
<body>

<div class="container mt-5">
    <h1 class="text-center">잠깐!</h1>

    <div class="alert alert-danger mt-4" role="alert">
        <strong>Message:</strong> <c:out value="${message}"/>. 에러가 발생했습니다. 다시 시도해주세요.
    </div>

    <table class="table table-striped mt-4">
        <tbody>
        <tr>
            <th>Status Code</th>
            <td><c:out value="${status_code}"/></td>
        </tr>
        <tr>
            <th>Exception Type</th>
            <td><c:out value="${exception_type}"/></td>
        </tr>
        <tr>
            <th>Exception</th>
            <td><c:out value="${exception}"/></td>
        </tr>
        <tr>
            <th>Request URI</th>
            <td><c:out value="${request_uri}"/></td>
        </tr>
        </tbody>
    </table>
</div>
</body>
</html>
