package com.recycle.server.controller;

import com.recycle.server.constants.PagingType;
import com.recycle.server.entity.Order;
import com.recycle.server.entity.User;
import com.recycle.server.entity.exception.InvalidSession;
import com.recycle.server.entity.model.PageToken;
import com.recycle.server.entity.response.TokenResponse;
import com.recycle.server.service.OrderService;
import com.recycle.server.util.ResUtils;
import com.recycle.server.util.token.PageTokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@Slf4j
public class OrderController {

    private HttpServletRequest request;
    private OrderService orderService;

    @Autowired
    public OrderController(HttpServletRequest request, OrderService orderService) {
        this.request = request;
        this.orderService = orderService;
    }

    @GetMapping("/order/{id}")
    public ResponseEntity getOrder(@PathVariable("id") Long orderId) {
        try {
            User user = User.build(request);
            Order order = orderService.getOrder(user, orderId);
            return ResUtils.ok(order);
        } catch (InvalidSession invalidSession) {
            return ResUtils.invalidReq(invalidSession.getMessage());
        } catch (Exception e) {
            log.error("[Get Order Failed] orderId: " + orderId, e);
            return ResUtils.unknownException(e);
        }
    }

    @GetMapping("/order/myself")
    public ResponseEntity batchGetOrder(@RequestParam(value = "pageToken", required = false) String pageToken) {
        try {
            User user = User.build(request);
            PageToken token = PageTokenUtils.parse(pageToken, PagingType.OFFSET);
            TokenResponse response = orderService.myOrders(user, token);
            return ResUtils.ok(response);
        } catch (InvalidSession invalidSession) {
            return ResUtils.invalidReq(invalidSession.getMessage());
        } catch (Exception e) {
            log.error("[Get My Orders Failed] pageToken: " + pageToken, e);
            return ResUtils.unknownException(e);
        }
    }

    @GetMapping("/order/recent")
    public ResponseEntity recentOrders(@RequestParam(value = "pageToken", required = false) String pageToken) {
        try {
            User user = User.build(request);
            PageToken token = PageTokenUtils.parse(pageToken, PagingType.OFFSET);
            TokenResponse response = orderService.recentOrders(user, token);
            return ResUtils.ok(response);
        } catch (InvalidSession invalidSession) {
            return ResUtils.invalidReq(invalidSession.getMessage());
        } catch (Exception e) {
            log.error("[Get Recent Orders Failed] pageToken: " + pageToken, e);
            return ResUtils.unknownException(e);
        }
    }

    @PostMapping("/order")
    public ResponseEntity createOrder(@RequestBody Order order) {
        try {
            User user = User.build(request);
            order = orderService.createOrder(user, order);
            return ResUtils.ok(order);
        } catch (InvalidSession invalidSession) {
            return ResUtils.invalidReq(invalidSession.getMessage());
        } catch (Exception e) {
            log.error("[Create Order Failed] order: " + order, e);
            return ResUtils.unknownException(e);
        }
    }

    @PostMapping("/order/delete/{id}")
    public ResponseEntity deleteOrder(@PathVariable("id") Long orderId) {
        try {
            User user = User.build(request);
            orderService.deleteOrder(user, orderId);
            return ResUtils.ok();
        } catch (InvalidSession invalidSession) {
            return ResUtils.invalidReq(invalidSession.getMessage());
        } catch (Exception e) {
            log.error("[Delete Order Failed] orderId: " + orderId, e);
            return ResUtils.unknownException(e);
        }
    }

    @PostMapping("/order/update")
    public ResponseEntity updateOrder(@RequestBody Order order) {
        try {
            User user = User.build(request);
            order = orderService.updateOrder(user, order);
            return ResUtils.ok(order);
        } catch (InvalidSession invalidSession) {
            return ResUtils.invalidReq(invalidSession.getMessage());
        } catch (Exception e) {
            log.error("[Update Order Failed] order: " + order, e);
            return ResUtils.unknownException(e);
        }
    }

}
