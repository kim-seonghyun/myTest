<%@ page import="java.util.Objects" %>
<%@ page import="com.nhnacademy.shoppingmall.user.domain.User" %>
<%@ page import="com.nhnacademy.shoppingmall.user.domain.User.Auth" %><%--
  Created by IntelliJ IDEA.
  User: gimseonghyeon
  Date: 12/6/23
  Time: 12:59 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <title>nhn아카데미 관리자 페이지</title>

</head>

<body>
<div class="mainContainer">
    <%
        User user = (User) session.getAttribute("user");
    %>
    <header class="p-3 bg-dark text-white">
        <div class="container">
            <div class="d-flex flex-wrap align-items-center justify-content-center justify-content-lg-start">

                <a href="/" class="d-flex align-items-center mb-2 mb-lg-0 text-white text-decoration-none">
                    <svg class="bi me-2" width="40" height="32" role="img" aria-label="Bootstrap">
                        <use xlink:href="#bootstrap"></use>
                    </svg>
                </a>

                <ul class="nav col-12 col-lg-auto me-lg-auto mb-2 justify-content-center mb-md-0">
                    <li><a href="/index.do" class="nav-link px-2 text-secondary">Home</a></li>
                    <%
                        if (Objects.nonNull(user)) {
                    %>
                    <li><a href="/mypage.do" class="nav-link px-2 text-white">마이페이지</a></li>
                    <% }
                    %>

                    <%
                        if (Objects.nonNull(user) && user.getUserAuth().equals(Auth.ROLE_ADMIN)) {

                    %>
                    <li><a href="/admin/index.do" class="nav-link px-2 text-white">관리자 페이지</a></li>
                    <a href="/admin/productList.do" class="btn btn-primary">상품 목록 보기</a>

                <%--                        <li><a href="/admin/index.do" class="nav-link px-2 text-white">관리자 페이지</a></li>--%>
                    <% }
                    %>

                </ul>

                <form class="col-12 col-lg-auto mb-3 mb-lg-0 me-lg-3">
                    <input type="search" class="form-control form-control-dark" placeholder="Search..."
                           aria-label="Search">
                </form>

                <div class="text-end">

                    <%
                        if (Objects.nonNull(user)) {
                    %>
                    <a><%= user.getUserName() %>님 안녕하세요</a>
                    <a class="btn btn-outline-light me-2" href="/logoutAction.do">로그아웃</a>
                    <%
                    } else {
                    %>
                    <a class="btn btn-outline-light me-2" href="/login.do">로그인</a>
                    <a class="btn btn-warning" href="/signup.do"> 회원가입</a>
                    <%
                        }
                    %>


                </div>
            </div>
        </div>
    </header>

    <main>
        <div class="album py-5 bg-light">
            <div class="container">
                <jsp:include page="${layout_content_holder}"/>
            </div>
        </div>

    </main>

    <footer class="text-muted py-5">
        <div class="container">
            <p class="float-end mb-1">
                <a href="#">Back to top</a>
            </p>
            <p class="mb-1">shoppingmall example is © nhnacademy.com</p>
        </div>
    </footer>

</div>

</body>

<body>
<div class="container">
    <header>
        <h1>관리자 페이지</h1>
    </header>
    <nav>
    </nav>
    <main>
        <jsp:include page="${content}"/>
    </main>
    <footer>
        <p>© 2023 nhnacademy.com</p>
    </footer>
</div>
</body>
</html>

