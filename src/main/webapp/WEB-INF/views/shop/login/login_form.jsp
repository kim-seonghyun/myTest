<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

</head>

<div style="margin: auto; width: 400px;">
    <div class="p-2">
        <form method="post" action="/loginAction.do" id="login-form">

            <h1 class="h3 mb-3 fw-normal">Please sign in</h1>

            <div class="form-floating">
                <input type="text" name="user_id" class="form-control" id="user_id" placeholder="회원 아이디">
                <label for="user_id">user id</label>
            </div>

            <div class="form-floating">
                <input type="password" name="user_password" class="form-control" id="user_password" placeholder="비밀번호"
                >
                <label for="user_password">password</label>
            </div>

            <button class="w-100 btn btn-lg btn-primary mt-3" type="submit">Sign in</button>

            <p class="mt-5 mb-3 text-muted">© 2022-2024</p>
        </form>
    </div>
</div>