<%@ page import="com.nhnacademy.shoppingmall.shoppingCart.domain.ShoppingCart" %>
<%@ page import="java.util.List" %>
<%@ page import="com.nhnacademy.shoppingmall.products.domain.Products" %><%--
  Created by IntelliJ IDEA.
  User: gimseonghyeon
  Date: 12/7/23
  Time: 12:19 PM
  To change this template use File | Settings | File Templates.
--%>


<%
    List<Products> productList = (List<Products>)request.getAttribute("productList");
    List<ShoppingCart> cartList = (List<ShoppingCart>)request.getAttribute("cartList");
%>

<table>
    <tr>
        <th>ModelName</th>
        <th>UnitCost</th>
        <th>Quantity</th>
        <th>Total</th>
    </tr>

    <%
        if(cartList != null && !cartList.isEmpty()){
            for(ShoppingCart shoppingCart : cartList){
                int quantity = shoppingCart.getQuantity();
                for(Products product : productList){
                    if(product.getProductId() == shoppingCart.getProductId()){
    %>
    <tr>
        <td><%= product.getModelName() %></td>
        <td><%= product.getUnitCost() %></td>
        <td><%= quantity %></td>
        <td><%= product.getUnitCost() * quantity %></td>
    </tr>
    <%
                    }
                }
            }
        }
    %>

</table>