<%--
  Created by IntelliJ IDEA.
  User: gimseonghyeon
  Date: 12/6/23
  Time: 1:24 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div id="admin-index" class="d-flex flex-column justify-content-center align-items-center" style="height: 80vh;">
    <h2 class="mb-5">관리자 페이지</h2>
    <div class="admin-nav">
        <a href="/admin/productList.do" class="btn btn-primary btn-lg m-3">모든 상품 보기</a>
        <a href="/admin/productRegistration.do" class="btn btn-success btn-lg m-3">상품 등록하기</a>
    </div>
</div>
