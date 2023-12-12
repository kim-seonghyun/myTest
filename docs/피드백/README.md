# 피드백

## ERD

- 로그인한 사용자만이 장바구니에 상품을 담을 수 있습니다.
  - 현재 ERD 설계에서는 비회원이 만약 장바구니를 사용이 가능했으면 회원과 비회원의 장바구니를 데이터베이스에서 식별이 불가능 했을 것 같습니다.
- 상품에 최대 3개의 카테고리가 담는게 요구 사항이었는데, 현재 ERD 설계에서는 불가능한 구조입니다...

## UserRepositoryImpl

- ResultSet을 `close()` 하는 것은 좋았지만 `close()` 하기 전 Null 체크도 해주시기 바랍니다. 
  - SonarLint를 설치하셨다면 SonarLint에서 경고를 주었을 것입니다.
- `try-with-resources`를 활용하면 이 문제도 해결 가능합니다.

```java
finally {
    try {
        rs.close();
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }
}
```

## 회원 가입

- 아래 코드는 회원 가입 컨트롤러 코드입니다.
- 현재, Service 계층에서 throw를 던저주고 있고, 그걸 Controller에서 캐치해서 또 다시 throw를 하는 형식입니다.
- FrontServlet의 역할을 이해했다면 이런 코드는 사실 필요가 없습니다. 한 번 FrontServlet의 역할을 공부해면 좋을 것 같습니다.
  - 하나의 요청(브라우저)이 들어오면 FrontServlet 부터 시작하는 요청의 흐름을 한 번 그림으로 그려보시면 쉽게 이해할 수 있습니다.


```java
public String execute(HttpServletRequest req, HttpServletResponse resp) {
    String id = req.getParameter("user_id");
    String name = req.getParameter("user_name");
    String password = req.getParameter("user_password");
    String userBirth = req.getParameter("user_birth");

    try {
        User user = new User(id, name, password, userBirth, Auth.ROLE_USER, 1_000_000, LocalDateTime.now(), null);
        log.debug(user.toString());
        userService.saveUser(user);
        return "redirect:/index.do";
    } catch (UserAlreadyExistsException e) {
        log.debug("유저가 이미 존재함 !");
        throw new UserAlreadyExistsException(e.getMessage());
    } catch (RuntimeException e) {
        log.debug("런타임 에러 발생 !");
        throw new RuntimeException(e.getMessage());
    }
}
```

## 장바구니

- 장바구니 추가를 클릭했지만 화면에 변화가 없어서 들어갔는지 안들갔는지 확인이 불가능한게 조금 아쉽습니다.
- 장바구니 상품 중복을 로직으로 처리했지만, 데이터베이스 설계에서도 처리가 가능할 것 같습니다.
- 주문을 하고 난 후 장바구니에 상품이 여전히 남아 있습니다...


## 주문

- 주문은 `/order.do` GET으로 보내고 있습니다. 
- HTTP 메서드에서 GET vs POST 차이점을 한 번 공부해 보시면 좋을 것 같습니다.
  - 네트워크를 공부하면 금방 차이점을 알게 될 거에요~

## index 페이지

- 카테고리 선택해서 보여주는 화면을 `POST` 메서드로 구현하였지만 카테고리 아이디를 가지고 `GET` 메서드로 구현하는 것이 조금 더 일반적입니다.


## 공통

- Session에 넣는 `user_id` 같은 값들을 상수로 정의해서 사용하시는 것이 좋습니다.
  - 회원 가입시 주는 포인트도 역시 상수로 관리 
- 값들을 확인하려고 찍은 log들의 레벨이 `debug`인 것은 좋은 것 같습니다.
- 관리자 권한 체크 시 HTTP Status Code 403을 반환하고 있지만, 한 번 401 vs 403도 한 번 공부해 보시기 바랍니다.

수고하셨습니다. 👏👏