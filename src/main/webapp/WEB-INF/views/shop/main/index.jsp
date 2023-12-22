<%@ page import="com.nhnacademy.shoppingmall.products.domain.Products" %>
<%@ page import="java.util.List" %>
<%@ page import="com.nhnacademy.shoppingmall.categories.domain.Categories" %>
<%@ page import="java.util.Objects" %><%--
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
    List<Categories> categoriesList = (List<Categories>) request.getAttribute("categories");
    int totalPageSize = (int) request.getAttribute("totalPageSize");
%>


<div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 g-3">
    <form method="POST" action="/index.do">
        <div class="form-floating">
            <select class="form-select" id="categoryId" name="categoryId" required>
                <option selected value="">카테고리 선택</option>
                <%
                    if(Objects.nonNull(categoriesList)){
                        for (Categories category : categoriesList) {
                %>
                <option value="<%=category.getCategoryID()%>"><%=category.getCategoryName()%></option>
                <%
                        }}
                %>
            </select>
            <label for="categoryId">카테고리 ID</label>
        </div>
        <input type="submit" value="submit">
    </form>
    <% for (Products products : productsList) { %>
    <div class="col">
        <div class="card shadow-sm">
            <img class="card-img-top" src="<%= products.getProductImage() %>" onerror="this.src='resources/no-image.png';" alt="resources/no-image.png" width="100%" height="225">
            <div class="card-body">
                <h5 class="card-title text-primary">상품 이름 : <%= products.getModelName() %></h5>
                <p class="card-text"><small class="text-muted">상품 설명 : </small><%= products.getDescription() %></p>
                <p class="card-text"><small class="text-muted">상품 가격 : </small><%= products.getUnitCost() %></p>
                <p class="card-text"><small class="text-muted">상품 번호 : </small><%= products.getProductId() %></p>

                <div class="d-flex justify-content-between align-items-center">
                    <div class="btn-group">
                        <button type="button" onclick="location.href='/addShoppingCart.do?productId=<%= products.getProductId() %>'" class="btn btn-sm btn-outline-secondary">장바구니에 추가</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <% } %>
    <div class="pagination">
        <%
            for(int i = 1; i <= totalPageSize; i++) {
        %>
        <a href="<%= "/index.do?page=" + i %>"> <%= i %> </a>
        <%
            }
        %>
    </div>
</div>