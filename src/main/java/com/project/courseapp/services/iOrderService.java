package com.project.courseapp.services;

import com.project.courseapp.dtos.OrderDTO;
import com.project.courseapp.models.Order;
import com.project.courseapp.models.User;

import java.util.List;

public interface iOrderService {
    Order createOrder(OrderDTO orderDTO);

    Order getOrder(long id);

    Order updateOrder(long id ,OrderDTO orderDTO);

    void deleteOrder(long id);

    List<Order> getAllOrders(long userId);
}
