package com.nhnacademy.shoppingmall.controller.auth;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping.Method;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.order.domain.Orders;
import com.nhnacademy.shoppingmall.order.repository.OrderRepository;
import com.nhnacademy.shoppingmall.order.repository.impl.OrderRepositoryImpl;
import com.nhnacademy.shoppingmall.order.services.OrderServices;
import com.nhnacademy.shoppingmall.order.services.impl.OrderServicesImpl;
import com.nhnacademy.shoppingmall.orderdetail.repository.OrderDetailRepository;
import com.nhnacademy.shoppingmall.orderdetail.repository.impl.OrderDetailRepositoryImpl;
import com.nhnacademy.shoppingmall.products.repository.ProductsRepository;
import com.nhnacademy.shoppingmall.products.repository.impl.ProductRepositoryImpl;
import com.nhnacademy.shoppingmall.shoppingCart.repository.impl.ShoppingCartRepositoryImpl;
import com.nhnacademy.shoppingmall.shoppingCart.service.ShoppingCartService;
import com.nhnacademy.shoppingmall.shoppingCart.service.impl.ShoppingCartServiceImpl;
import com.nhnacademy.shoppingmall.thread.channel.RequestChannel;
import com.nhnacademy.shoppingmall.thread.request.impl.PointChannelRequest;
import com.nhnacademy.shoppingmall.user.domain.User;
import com.nhnacademy.shoppingmall.user.repository.UserRepository;
import com.nhnacademy.shoppingmall.user.repository.impl.UserRepositoryImpl;
import com.nhnacademy.shoppingmall.user.service.UserService;
import com.nhnacademy.shoppingmall.user.service.impl.UserServiceImpl;
import java.time.LocalDateTime;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping(method = Method.GET, value = "/order.do")
public class OrderController implements BaseController {
    OrderRepository orderRepository = new OrderRepositoryImpl(new UserRepositoryImpl());
    OrderDetailRepository orderDetailRepository = new OrderDetailRepositoryImpl(new ProductRepositoryImpl(),
            new OrderRepositoryImpl(new UserRepositoryImpl()));

    ShoppingCartService shoppingCartService = new ShoppingCartServiceImpl(new ShoppingCartRepositoryImpl());
    ProductsRepository productsRepository = new ProductRepositoryImpl();
    UserRepository userRepository = new UserRepositoryImpl();
    UserService userService = new UserServiceImpl(userRepository);
    OrderServices orderServices = new OrderServicesImpl(orderRepository, orderDetailRepository, shoppingCartService,
            productsRepository, userService);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession(false);
        if (Objects.isNull(session) || Objects.isNull(session.getAttribute("user"))) {
            throw new RuntimeException("로그인 후 사용해 주세요");
        }
        User user = (User) session.getAttribute("user");
        String userId = user.getUserId();
        LocalDateTime shipDate = LocalDateTime.now().plusDays(2);
        Orders orders = new Orders(userId, LocalDateTime.now(), shipDate);
        int orderId = orderServices.order(orders);
        int pointAfterOrder = userService.getPoint(userId);

        //쓰레드 호출.
        RequestChannel requestChannel = (RequestChannel) req.getServletContext().getAttribute("requestChannel");
        int totalCost = orderDetailRepository.getTotalCost(orderId);
        int pointToAdd = (int) (totalCost * (0.1));
        log.debug("totalcost={}", orderDetailRepository.getTotalCost(orderId));

        log.debug("pointToadd={}", pointToAdd);
        user.setUserPoint(pointAfterOrder);
        try {
            requestChannel.addRequest(new PointChannelRequest(userId, pointAfterOrder + pointToAdd));
        } catch (InterruptedException e) {
            throw new RuntimeException(e.getMessage());
        }
        req.setAttribute("pointAfterOrder", pointAfterOrder);
        req.setAttribute("pointToAdd", pointToAdd);
        req.setAttribute("totalCost", totalCost);
        return "shop/main/order_result";
    }
}
