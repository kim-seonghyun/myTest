<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="user" scope="session" type="com.nhnacademy.shoppingmall.user.domain.User"/>
<%@ page import="com.nhnacademy.shoppingmall.user.domain.User" %>
<%@ page import="com.nhnacademy.shoppingmall.address.domain.Address" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: gimseonghyeon
  Date: 12/5/23
  Time: 3:06 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" trimDirectiveWhitespaces="true" session="true" %>
<%
    List<Address> addresses = (List<Address>) session.getAttribute("addresses");
%>
<div style="margin: auto; width: 400px;">
    <div class="p-2">
        <h1 class="h3 mb-3 fw-normal">User Information</h1>

        <div class="form-floating">
            <input type="text" class="form-control" id="user_name" placeholder="User Name" value="${user.userName}"
                   readonly>
            <label for="user_name">User Name</label>
        </div>

        <div class="form-floating">
            <input type="date" class="form-control" id="user_birth" placeholder="User Birth" value="${user.userBirth}"
                   readonly>
            <label for="user_birth">User Birth</label>
        </div>

        <div class="form-floating">
            <input type="text" class="form-control" id="user_auth" placeholder="User Auth" value="${user.userAuth}"
                   readonly>
            <label for="user_auth">User Auth</label>
        </div>

        <div class="form-floating">
            <input type="text" class="form-control" id="user_point" placeholder="User Point" value="${user.userPoint}"
                   readonly>
            <label for="user_point">User Point</label>
        </div>

        <div class="form-floating">
            <input type="text" class="form-control" id="created_at" placeholder="Created At" value="${user.createdAt}"
                   readonly>
            <label for="created_at">Created At</label>
        </div>

        <div class="form-floating">
            <input type="text" class="form-control" id="latest_login_at" placeholder="Latest Login At"
                   value="${user.latestLoginAt}" readonly>
            <label for="latest_login_at">Latest Login At</label>
        </div>


        <% for (Address address : addresses) { %>
        <div class="form-floating mb-3">
            <input type="text" class="form-control" id="address" placeholder="Address"
                   value=<%= address.getAddress_id() %> readonly>
            <label for="address">Address </label>
            <a href="/addressUpdate.do?address_id=<%= address.getAddress_id() %>" type="button">change Address</a>
            <a href="/addressDelete.do?address_id=<%= address.getAddress_id() %>" type="button">delete Address</a>
        </div>
        <% } %>

        <div class="text-center">
            <a href="/update.do" class="btn btn-primary mt-3">유저 정보수정하기</a>
            <a href="/addressRegistration.do" class="btn btn-success mt-3">주소 등록하기</a>
            <a href="/deleteUser.do?userId=<%= user.getUserId() %>" class="btn btn-info mt-3" type="button">회원 탈퇴하기</a>
            <p class="mt-5 mb-3 text-muted">© 2022-2024</p>
        </div>

    </div>
