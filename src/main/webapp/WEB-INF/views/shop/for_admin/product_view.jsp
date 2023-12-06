<%--
  Created by IntelliJ IDEA.
  User: gimseonghyeon
  Date: 12/6/23
  Time: 8:12 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.nhnacademy.shoppingmall.Products.domain.Products" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<%
    List<Products> productsList = (List<Products>)request.getAttribute("productsList");
%>
<div class="container">
    <table class="table">
        <thead>
        <tr>

            <th>CategoryID</th>
            <th>ModelNumber</th>
            <th>ModelName</th>
            <th>ProductImage</th>
            <th>UnitCost</th>
            <th>Description</th>
        </tr>
        </thead>
        <tbody>
        <% for (Products product : productsList) { %>
        <tr>
            <td><%= product.getCategoryId() %></td>
            <td><%= product.getModelNumber() %></td>
            <td><%= product.getModelName() %></td>
            <td><img src="<%= product.getProductImage() %>" alt="Product Image"></td>
            <td><%= product.getUnitCost() %></td>
            <td><%= product.getDescription() %></td>
            <td><a href="/admin/updateProduct.do?productId=<%= product.getProductId() %>">수정하기</a></td>
            <td><a href="/admin/deleteProduct.do?productId=<%= product.getProductId() %>">삭제하기</a></td>

        </tr>
        <% } %>
        </tbody>
    </table>
</div>
