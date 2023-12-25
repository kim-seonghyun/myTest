package com.nhnacademy.shoppingmall.common.filter;

import com.nhnacademy.shoppingmall.products.domain.RecentViewProducts;
import java.util.ArrayList;
import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@WebFilter(urlPatterns = "/")
public class WelcomePageFilter extends HttpFilter {

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        //todo#9 /요청이 오면 welcome page인 index.do redirect 합니다.
        // session이 존재하지 않는다면 -> 생성, 존재한다면 그대로 쓰기.
        HttpSession session = req.getSession(true);
        if (session.getAttribute("recentViewProducts") == null) {
            RecentViewProducts recentViewProducts = new RecentViewProducts(new ArrayList<>());
            session.setAttribute("recentViewProducts", recentViewProducts);
        }

        log.debug("welcomePageFilter 실행");
        if (req.getServletPath().equals("/")) {
            res.sendRedirect("/index.do");
        }else{
            chain.doFilter(req,res);
        }
    }
}
