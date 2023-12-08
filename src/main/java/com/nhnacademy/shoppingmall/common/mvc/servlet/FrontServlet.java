package com.nhnacademy.shoppingmall.common.mvc.servlet;

import static javax.servlet.RequestDispatcher.ERROR_EXCEPTION;
import static javax.servlet.RequestDispatcher.ERROR_EXCEPTION_TYPE;
import static javax.servlet.RequestDispatcher.ERROR_MESSAGE;
import static javax.servlet.RequestDispatcher.ERROR_REQUEST_URI;
import static javax.servlet.RequestDispatcher.ERROR_STATUS_CODE;

import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.common.mvc.view.ViewResolver;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.common.mvc.controller.ControllerFactory;

import java.io.IOException;
import java.sql.Connection;
import javax.servlet.ServletConfig;
import javax.servlet.annotation.MultipartConfig;
import lombok.extern.slf4j.Slf4j;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@WebServlet(name = "frontServlet", urlPatterns = {"*.do"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 3,  // 1 MB
        maxFileSize = 1024 * 1024 * 50, // 10 MB
        maxRequestSize = 1024 * 1024 * 100 // 100 MB
)
public class FrontServlet extends HttpServlet {
    private ControllerFactory controllerFactory;
    private ViewResolver viewResolver;

    @Override
    public void init(ServletConfig config) throws ServletException {
        //todo#7-1 controllerFactory를 초기화 합니다.
        controllerFactory = (ControllerFactory) config.getServletContext()
                .getAttribute(ControllerFactory.CONTEXT_CONTROLLER_FACTORY_NAME);

        //todo#7-2 viewResolver를 초기화 합니다.
        viewResolver = new ViewResolver();
    }

    // local variable 하기 때문에 Thread-safety하다.
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) {
        DbConnectionThreadLocal.initialize();
        try {
            //todo#7-3 Connection pool로 부터 connection 할당 받습니다. connection은 Thread 내에서 공유됩니다.
            // controller 받아옴
            log.debug(req.getServletPath());
            BaseController baseController = (BaseController) controllerFactory.getController(req);
            log.debug("Factory = {}", controllerFactory);
            log.debug("baseController = {}", baseController);
            log.debug(baseController.getClass().getName());

            // controller로 부터 JSP 경로 받아옴.
            String viewName = baseController.execute(req, resp);

            if (viewResolver.isRedirect(viewName)) {
                String redirectUrl = viewResolver.getRedirectUrl(viewName);
                log.debug("redirectUrl:{}", redirectUrl);
                //todo#7-6 redirect: 로 시작하면 해당 url로 redirect 합니다.
                resp.sendRedirect(viewResolver.getRedirectUrl(redirectUrl));
            } else {
                String layout = viewResolver.getLayOut(viewName);
                log.debug("viewName:{}", viewResolver.getPath(viewName));
                req.setAttribute(ViewResolver.LAYOUT_CONTENT_HOLDER, viewResolver.getPath(viewName));
                RequestDispatcher rd = req.getRequestDispatcher(layout);
                rd.include(req, resp);
            }
        } catch (Exception e) {
            log.error("error:{}", e);
            DbConnectionThreadLocal.setSqlError(true);
            req.setAttribute("status_code", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            req.setAttribute("exception_type", e.getClass().getName());
            req.setAttribute("message", e.getMessage());
            req.setAttribute("exception", e);
            req.setAttribute("request_uri", req.getRequestURI());
            RequestDispatcher rd = req.getRequestDispatcher(viewResolver.getPath("error"));
            try {
                rd.forward(req, resp);
            } catch (ServletException ex) {
                throw new RuntimeException(ex);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            //todo#7-5 예외가 발생하면 해당 예외에 대해서 적절한 처리를 합니다.

        } finally {
            //todo#7-4 connection을 반납합니다.
            DbConnectionThreadLocal.reset();
        }
    }

}