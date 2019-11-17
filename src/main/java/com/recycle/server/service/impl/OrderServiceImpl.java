package com.recycle.server.service.impl;

import com.recycle.server.annotations.ValidateSession;
import com.recycle.server.constants.Constants;
import com.recycle.server.constants.PagingType;
import com.recycle.server.constants.ResponseStrings;
import com.recycle.server.constants.enums.OrderStatus;
import com.recycle.server.constants.enums.UserRole;
import com.recycle.server.dao.mapper.OrderMapper;
import com.recycle.server.dao.mapper.UserMapper;
import com.recycle.server.entity.Order;
import com.recycle.server.entity.User;
import com.recycle.server.entity.model.PageToken;
import com.recycle.server.entity.response.TokenResponse;
import com.recycle.server.service.OrderService;
import com.recycle.server.util.DateExtUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service("orderService")
public class OrderServiceImpl implements OrderService {

    private OrderMapper orderMapper;
    private UserMapper userMapper;

    @Autowired
    public OrderServiceImpl(OrderMapper orderMapper, UserMapper userMapper) {
        this.orderMapper = orderMapper;
        this.userMapper = userMapper;
    }

    private void validateOrder(Order order) throws Exception {
        if (order.getStatus() != null) {
            return;
        }
        if (order.getBottleCount() != null && order.getBottleCount() > 0) {
            return;
        }
        if (order.getCartonCount() != null && order.getCartonCount() > 0) {
            return;
        }
        if (order.getScrapIronCount() != null && order.getScrapIronCount() > 0) {
            return;
        }
        if (Strings.isNotBlank(order.getOther())) {
            return;
        }
        if (Strings.isNotBlank(order.getRemark())) {
            return;
        }
        throw new Exception(ResponseStrings.INVALID_ORDER);
    }

    @Override
    @ValidateSession
    public Order createOrder(User user, Order order) throws Exception {
        if (!user.getId().equals(order.getUserId())) {
            throw new Exception(ResponseStrings.EMPTY_MESSAGE);
        }
        validateOrder(order);
        order.setStatus(OrderStatus.WAITING.getCode());
        order.setCreatedTime(DateExtUtils.currentTimeMill());
        orderMapper.createOrder(order);
        return order;
    }

    @Override
    @ValidateSession
    public void deleteOrder(User user, Long orderId) throws Exception {
        Order oldOrder = orderMapper.selectOrder(orderId);
        if (!oldOrder.getUserId().equals(user.getId())) {
            throw new Exception(ResponseStrings.EMPTY_MESSAGE);
        }
        orderMapper.updateOrder(
                Order.builder()
                        .id(orderId)
                        .status(OrderStatus.DELETE.getCode())
                        .modifiedTime(DateExtUtils.currentTimestamp())
                        .build(),
                false
        );
    }

    @Override
    @ValidateSession
    public Order updateOrder(User user, Order order) throws Exception {
        // validate input
        if (!order.getUserId().equals(user.getId())) {
            throw new Exception(ResponseStrings.EMPTY_MESSAGE);
        }
        validateOrder(order);
        user = userMapper.selectById(user.getId());
        Order oldOrder = orderMapper.selectOrder(order.getId());
        if (!oldOrder.getUserId().equals(user.getId())) {
            throw new Exception(ResponseStrings.EMPTY_MESSAGE);
        }
        if (!UserRole.ADMIN.getCode().equals(user.getRole())
                && OrderStatus.PROCESSING.getCode().equals(order.getStatus())) {
            throw new Exception(ResponseStrings.ERROR_PROCESSING);
        }
        // set default value
        if (order.getStatus() == null) {
            order.setStatus(oldOrder.getStatus());
        }
        order.setModifiedTime(DateExtUtils.currentTimestamp());
        orderMapper.updateOrder(order, true);
        return order;
    }

    @Override
    @ValidateSession
    public Order getOrder(User user, Long orderId) throws Exception {
        Order order = orderMapper.selectOrder(orderId);
        User userInfo = userMapper.selectById(order.getUserId());
        order.setUserInfo(userInfo);
        return order;
    }

    private void fillOrderUserInfo(List<Order> orders) {
        Set<Integer> userIds = orders.stream().map(Order::getUserId).collect(Collectors.toSet());
        if (userIds == null || userIds.size() == 0) {
            return;
        }
        List<User> userList = userMapper.selectByIds(userIds);
        Map<Integer, User> userMap = userList.stream().collect(Collectors.toMap(User::getId, item -> item));
        for (Order order : orders) {
            order.setUserInfo(userMap.get(order.getUserId()));
        }
    }

    @Override
    @ValidateSession
    public TokenResponse myOrders(User user, PageToken pageToken) throws Exception {
        List<Order> orders = orderMapper.selectOrdersByUserId(
                user.getId(),
                pageToken != null ? pageToken.getNextId() : null,
                Constants.DEFAULT_PAGE_SIZE
        );
        fillOrderUserInfo(orders);
        Long lastId = null;
        for (Order order : orders) {
            lastId = order.getCreatedTime();
        }
        return TokenResponse.build(
                orders,
                PageToken.builder()
                        .nextId(lastId)
                        .preId(pageToken != null ? pageToken.getNextId() : null)
                        .type(PagingType.OFFSET.getCode())
                        .build()
        );
    }

    @Override
    @ValidateSession
    public TokenResponse recentOrders(User user, PageToken pageToken) throws Exception {
        List<Order> orders = orderMapper.recentOrders(
                pageToken != null ? pageToken.getNextId() : null,
                Constants.DEFAULT_PAGE_SIZE
        );
        fillOrderUserInfo(orders);
        Long minCreatedTime = null;
        for (Order order : orders) {
            minCreatedTime = order.getCreatedTime();
        }
        return TokenResponse.build(
                orders,
                PageToken.builder()
                        .nextId(minCreatedTime)
                        .preId(pageToken != null ? pageToken.getNextId() : null)
                        .type(PagingType.OFFSET.getCode())
                        .build()
        );
    }

}
