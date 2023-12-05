<%--
  Created by IntelliJ IDEA.
  User: gimseonghyeon
  Date: 12/5/23
  Time: 9:46 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" trimDirectiveWhitespaces="true" session="true" %>
<form method="post" action="/update.do" class="p-5 mx-auto" style="max-width: 400px;">
  <div>
    <h1 class="h3 mb-3 fw-normal text-center">user information correction</h1>

    <div class="form-floating mb-3">
      <input type="text" class="form-control" name="user_name" id="user_name" placeholder="User Name" value="${user.userName}" maxlength="50" required>
      <label for="user_name">User Name</label>
    </div>

    <div class="form-floating mb-3">
      <input type="date" class="form-control" name="user_birth" id="user_birth" placeholder="User Birth" value="${user.userBirth}" required>
      <label for="user_birth">User Birth</label>
    </div>

    <div class="form-floating mb-3">
      <input type="password" class="form-control" name="user_password" id="user_password" placeholder="User password" value="${user.userPassword}" max="200" required>
      <label for="user_password">User Password</label>
    </div>

    <button type="submit" class="btn btn-primary mb-3">제출</button>

    <p class="mt-5 mb-3 text-muted text-center">© 2022-2024</p>
  </div>
</form>