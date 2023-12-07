<%--
  Created by IntelliJ IDEA.
  User: gimseonghyeon
  Date: 12/6/23
  Time: 8:12 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="java.util.*" %>
<%@ page import="com.nhnacademy.shoppingmall.products.domain.Products" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
        <% for (Products products : productsList) { %>
        <tr>
            <td><%= products.getCategoryId() %></td>
            <td><%= products.getModelNumber() %></td>
            <td><%= products.getModelName() %></td>
            <td><%= products.getProductImage() %></td>
            <td><%= products.getUnitCost() %></td>
            <td><%= products.getDescription() %></td>
            <td><a href="/admin/updateProduct.do?productId=<%= products.getProductId() %>">수정하기</a></td>
            <td><a href="/admin/deleteProduct.do?productId=<%= products.getProductId() %>">삭제하기</a></td>

        </tr>
        <% } %>
        </tbody>
    </table>
    <a href="/admin/productRegistration.do" class="btn btn-success btn-lg m-3">상품 등록하기</a>
</div>
