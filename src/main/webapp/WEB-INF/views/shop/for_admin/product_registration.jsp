<%@ page import="com.nhnacademy.shoppingmall.categories.domain.Categories" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Objects" %><%--
  Created by IntelliJ IDEA.
  User: gimseonghyeon
  Date: 12/6/23
  Time: 12:44 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String categoryId = request.getParameter("categoryId") != null ? request.getParameter("categoryId") : "";
    String modelNumber = request.getParameter("modelNumber") != null ? request.getParameter("modelNumber") : "";
    String modelName = request.getParameter("modelName") != null ? request.getParameter("modelName") : "";
    String productImage = request.getParameter("productImage") != null ? request.getParameter("productImage") : "";
    String unitCost = request.getParameter("unitCost") != null ? request.getParameter("unitCost") : "";
    String description = request.getParameter("description") != null ? request.getParameter("description") : "";
    List<Categories> categoriesList = (List<Categories>) request.getAttribute("categories");
%>
<div style="margin: auto; width: 400px;">
    <div class="p-2">
        <h1 class="h3 mb-3 fw-normal">상품 등록</h1>
        <form action="/admin/productRegistration.do" method="post" enctype="multipart/form-data">
            <div class="form-floating">
                <select class="form-select" id="categoryId" name="categoryId" required>
                    <option selected>카테고리 선택</option>
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

            <div class="form-floating">
                <input type="text" id="modelNumber" name="modelNumber" value="<%=modelNumber%>"
                       class="form-control" placeholder="모델 번호" required>
                <label for="modelNumber">모델 번호</label>
            </div>
            <div class="form-floating">
                <input type="text" id="modelName" name="modelName" value="<%=modelName%>"
                       class="form-control" placeholder="모델 이름" required>
                <label for="modelName">모델 이름</label>
            </div>
            <div class="form-floating">
                <input type="file" id="productImage" name="productImage" class="form-control" required>
                <label for="productImage">상품 이미지</label>
            </div>
            <div class="form-floating">
                <input type="text" id="unitCost" name="unitCost" value="<%=unitCost%>" class="form-control"
                       placeholder="단위 가격" required>
                <label for="unitCost">단위 가격</label>
            </div>
            <div class="form-floating">
                <textarea id="description" name="description" class="form-control" placeholder="상품 설명"
                          required><%=description%></textarea>
                <label for="description">상품 설명</label>
            </div>
            <button type="submit" class="btn btn-primary mt-3">제출</button>
        </form>
    </div>
</div>
