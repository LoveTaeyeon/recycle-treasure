package com.recycle.server.service;

import com.recycle.server.entity.Order;
import com.recycle.server.entity.User;
import com.recycle.server.entity.model.PageToken;
import com.recycle.server.entity.response.TokenResponse;

public interface OrderService {

    Order createOrder(User user, Order order) throws Exception;

    void deleteOrder(User user, Long orderId) throws Exception;

    Order updateOrder(User user, Order order) throws Exception;

    Order getOrder(User user, Long orderId) throws Exception;

    TokenResponse myOrders(User user, PageToken pageToken) throws Exception;

    TokenResponse recentOrders(User user, PageToken pageToken) throws Exception;

}
