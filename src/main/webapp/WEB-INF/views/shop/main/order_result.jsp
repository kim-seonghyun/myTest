<%@ page import="com.nhnacademy.shoppingmall.user.domain.User" %><%--
  Created by IntelliJ IDEA.
  User: gimseonghyeon
  Date: 12/8/23
  Time: 2:19 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
    // session에서 user 객체를 가져옴
    User user = (User) session.getAttribute("user");
%>

<div id="admin-index" class="d-flex flex-column justify-content-center align-items-center" style="height: 80vh;"><h2
        class="mb-5">order result</h2>
    <div class="order-result"><% if (user != null) { %>
        <p>주문 결과: 사용자 포인트는 <%= user.getUserPoint() %>
        입니다.</p>
        <p>추후 <%= request.getAttribute("pointToAdd") %>만큼의 포인트가 적립됩니다.</p>
        <% } else { %> <p>로그인이 필요합니다.</p> <% } %></div>
</div>