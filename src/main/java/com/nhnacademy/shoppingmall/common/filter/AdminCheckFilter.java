package com.nhnacademy.shoppingmall.common.filter;

import com.nhnacademy.shoppingmall.user.domain.User;
import com.nhnacademy.shoppingmall.user.domain.User.Auth;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@WebFilter(urlPatterns = "/admin/*")
public class AdminCheckFilter extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        //todo#11 /admin/ 하위 요청은 관리자 권한의 사용자만 접근할 수 있습니다. ROLE_USER가 접근하면 403 Forbidden 에러처리
        if (req.getServletPath().contains("/admin")) {
            User user = (User) req.getAttribute("user");
            if (Objects.nonNull(user)) {
                if (user.getUserAuth().equals(Auth.ROLE_ADMIN)) {
                    log.debug("admin 입니다");
                    chain.doFilter(req,res);
                }else if(user.getUserAuth().equals(Auth.ROLE_USER)){
                    res.sendError(403, "admin only");
                }
            }else{
                res.sendError(403, "admin only");
            }
        }else {
            log.debug("admincheckFilter : 다음 필터로 넘김");
            chain.doFilter(req,res);
        }
    }
}
