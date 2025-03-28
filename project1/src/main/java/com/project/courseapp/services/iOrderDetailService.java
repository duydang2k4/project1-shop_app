package com.project.courseapp.services;

import com.project.courseapp.dtos.OrderDetailDTO;
import com.project.courseapp.models.OrderDetail;

import java.util.List;

public interface iOrderDetailService {
    OrderDetail createOrderDetail(OrderDetailDTO newOrderDetail);
    OrderDetail getOrderDetail(long id);
    OrderDetail updateOrderDetail(long id, OrderDetailDTO updateOrderDetail);
    void deleteOrderDetail(long orderDetailId);
    List<OrderDetail> getAllOrderDetails(long orderId);
}
