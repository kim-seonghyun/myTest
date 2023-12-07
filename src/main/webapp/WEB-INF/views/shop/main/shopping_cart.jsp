<%@ page import="com.nhnacademy.shoppingmall.shoppingCart.domain.ShoppingCart" %>
<%@ page import="java.util.List" %>
<%@ page import="com.nhnacademy.shoppingmall.products.domain.Products" %><%--
  Created by IntelliJ IDEA.
  User: gimseonghyeon
  Date: 12/7/23
  Time: 12:19 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    List<Products> productList = (List<Products>) request.getAttribute("productList");
    List<ShoppingCart> cartList = (List<ShoppingCart>) request.getAttribute("cartList");
%>

<div class="container">
    <h2 class="mb-5">장바구니</h2>
    <table class="table table-striped">
        <thead>
        <tr>
            <th>ModelName</th>
            <th>UnitCost</th>
            <th>Quantity</th>
            <th>Total Cost</th>
            <th>Controll Quantity</th>
            <th>Delete product</th>
        </tr>
        </thead>
        <tbody>
        <%
            if (cartList != null && !cartList.isEmpty()) {
                for (ShoppingCart shoppingCart : cartList) {
                    int quantity = shoppingCart.getQuantity();
                    for (Products product : productList) {
                        if (product.getProductId() == shoppingCart.getProductId()) {
        %>
        <tr>
            <td><%= product.getModelName() %></td>
            <td><%= product.getUnitCost() %></td>
            <td><%= quantity %></td>
            <td><%= product.getUnitCost() * quantity %></td>
            <td><a type="button" href="/updateShoppingCart.do?productId=<%=product.getProductId()%>&quantity=<%= quantity +1  %>">up</a> <a href="/updateShoppingCart.do?productId=<%=product.getProductId()%>&quantity=<%= quantity -1%>">down</a></td>
            <td><a type="button" href="/deleteShoppingCart.do?productId=<%=product.getProductId()%>">delete</a> </td>
            <td></td>
        </tr>
        <%
                        }
                    }
                }
            }
        %>
        </tbody>
    </table>
</div>
