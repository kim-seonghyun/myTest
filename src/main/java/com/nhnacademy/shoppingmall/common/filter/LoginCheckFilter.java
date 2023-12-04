package com.nhnacademy.shoppingmall.common.filter;

import com.nhnacademy.shoppingmall.user.domain.User;
import com.nhnacademy.shoppingmall.user.domain.User.Auth;
import java.io.IOException;
import java.util.Objects;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@WebFilter(urlPatterns = "/mypage/*")
public class LoginCheckFilter extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        //todo#10 /mypage/ 하위경로의 접근은 로그인한 사용자만 접근할 수 있습니다.

//        if (req.getServletPath().contains("/mypage")) {
        User user = (User) req.getAttribute("user");
        if (Objects.nonNull(user)) {
            if (user.getUserAuth().equals(Auth.ROLE_USER)) {
                chain.doFilter(req, res);
            } else {
                res.sendRedirect("/login.do");
            }
        } else {
            res.sendRedirect("/login.do");
        }
//        } else {
//            log.debug("loginCheckFilter 다음 필터로 넘김");
//            chain.doFilter(req, res);
//        }

    }
}