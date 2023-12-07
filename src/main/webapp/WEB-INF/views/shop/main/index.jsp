<%@ page import="com.nhnacademy.shoppingmall.products.domain.Products" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: nhn
  Date: 2023/11/08
  Time: 10:20 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    // 서버에서 이미지 리스트를 가져옵니다.
    List<Products> productsList = (List<Products>) request.getAttribute("productsList");
%>

<div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 g-3">
    <% for (Products products : productsList) { %>
    <div class="col">
        <div class="card shadow-sm">
            <img class="card-img-top" src="<%= products.getProductImage() %>" alt="src/main/webapp/resources/no-image.png" width="100%" height="225">
            <div class="card-body">
                <p class="card-text">This is a wider card with supporting text below as a natural lead-in to additional content. This content is a little bit longer.</p>
                <div class="d-flex justify-content-between align-items-center">
                    <div class="btn-group">
                        <button type="button" onclick="location.href='/addShoppingCart.do?productId=<%= products.getProductId() %>'" class="btn btn-sm btn-outline-secondary">장바구니에 추가</button>
                        <button type="button" class="btn btn-sm btn-outline-secondary">Edit</button>
                    </div>
                    <small class="text-muted">9 mins</small>
                </div>
            </div>
        </div>
    </div>
    <% } %>
</div>

