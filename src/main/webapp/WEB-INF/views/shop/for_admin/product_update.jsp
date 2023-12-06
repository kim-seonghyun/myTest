<%@ page import="com.nhnacademy.shoppingmall.categories.domain.Categories" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Objects" %>
<%@ page import="com.nhnacademy.shoppingmall.products.domain.Products" %><%--
  Created by IntelliJ IDEA.
  User: gimseonghyeon
  Date: 12/6/23
  Time: 12:44 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  Products products = (Products) request.getAttribute("products");
  List<Categories> categoriesList = (List<Categories>) request.getAttribute("categories");
%>

<div style="margin: auto; width: 400px;">
  <div class="p-2">
    <h1 class="h3 mb-3 fw-normal">상품 등록</h1>
    <form action="/admin/productUpdate.do" method="post" enctype="multipart/form-data">
      <div class="form-floating">
        <select class="form-select" id="categoryId" name="categoryId"  required>
          <option selected value="<%= products.getCategoryId() %>">카테고리 선택</option>
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
      <input type="hidden" name="productId" value="<%= products.getProductId()%>">
      <div class="form-floating">
        <input type="text" id="modelNumber" name="modelNumber" value="<%=products.getModelNumber()%>"
               class="form-control" placeholder="모델 번호" required>
        <label for="modelNumber">모델 번호</label>
      </div>
      <div class="form-floating">
        <input type="text" id="modelName" name="modelName" value="<%=products.getModelName()%>"
               class="form-control" placeholder="모델 이름" required>
        <label for="modelName">모델 이름</label>
      </div>
      <div class="form-floating">
        <input type="text" id="unitCost" name="unitCost" value="<%=products.getUnitCost()%>" class="form-control"
               placeholder="단위 가격" required>
        <label for="unitCost">단위 가격</label>
      </div>
      <div class="form-floating">
                <textarea id="description" name="description" class="form-control" placeholder="상품 설명"
                          required><%=products.getDescription()%></textarea>
        <label for="description">상품 설명</label>
      </div>
      <button type="submit" class="btn btn-primary mt-3">제출</button>
    </form>
  </div>
</div>
