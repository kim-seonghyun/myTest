<%@ page import="com.nhnacademy.shoppingmall.address.domain.Address" %><%--
  Created by IntelliJ IDEA.
  User: gimseonghyeon
  Date: 12/5/23
  Time: 11:39 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Address address = (Address) request.getAttribute("address");
    String address_id = address != null ? address.getAddress_id() : "";
    String address_line1 = address != null ? address.getAddress_line1() : "";
    String address_line2 = address != null ? address.getAddress_line2() : "";
    String city = address != null ? address.getCity() : "";
    String sido = address != null ? address.getSido() : "";
    String postal_code = address != null ? address.getPostal_code() : "";
%>
<div style="margin: auto; width: 400px;">
    <div class="p-2">

        <% if (address == null) { %>
        <h1 class="h3 mb-3 fw-normal">주소 생성</h1>
        <form action="/addressRegistration.do" method="post">
            <div class="form-floating">
                <input type="text" id="address_id" name="address_id" value="<%=address_id%>" class="form-control"
                       placeholder="주소 nickname"  required maxlength="">
                <label for="address_id">Address nickname</label>
            </div>
                <% } else { %>
                    <h1 class="h3 mb-3 fw-normal">주소 업데이트</h1>
            <form action="/addressUpdate.do" method="post">
                <div class="form-floating">
                    <input type="text" name="address_id" value="<%=address_id%>" class="form-control"
                           placeholder="주소 nickname" readonly>
                    <label for="address_id">Address nickname</label>
                </div>
                <% } %>

                <div class="form-floating">
                    <input type="text" id="address_line1" name="address_line1" value="<%=address_line1%>"
                           class="form-control" placeholder="Address Line 1" required>
                    <label for="address_line1">Address Line 1</label>
                </div>
                <div class="form-floating">
                    <input type="text" id="address_line2" name="address_line2" value="<%=address_line2%>"
                           class="form-control" placeholder="Address Line 2" required>
                    <label for="address_line2">Address Line 2</label>
                </div>
                <div class="form-floating">
                    <input type="text" id="city" name="city" value="<%=city%>" class="form-control" placeholder="City"
                           required>
                    <label for="city">City</label>
                </div>
                <div class="form-floating">
                    <input type="text" id="sido" name="sido" value="<%=sido%>" class="form-control" placeholder="Sido"
                           required>
                    <label for="sido">Sido</label>
                </div>
                <div class="form-floating">
                    <input type="text" id="postal_code" name="postal_code" value="<%=postal_code%>" class="form-control"
                           placeholder="Postal Code" required>
                    <label for="postal_code">Postal Code</label>
                </div>
                <button type="submit" class="btn btn-primary mt-3">submit</button>
            </form>
    </div>
</div>
