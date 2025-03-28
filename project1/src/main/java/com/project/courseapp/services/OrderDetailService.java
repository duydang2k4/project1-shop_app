package com.project.courseapp.services;

import com.project.courseapp.dtos.OrderDetailDTO;
import com.project.courseapp.execeptions.DataNotFoundException;
import com.project.courseapp.models.Order;
import com.project.courseapp.models.OrderDetail;
import com.project.courseapp.models.Product;
import com.project.courseapp.repositories.OrderDetailRepository;
import com.project.courseapp.repositories.OrderRepository;
import com.project.courseapp.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@RequiredArgsConstructor
public class OrderDetailService implements iOrderDetailService{
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final OrderDetailRepository orderDetailRepository;

    @Override
    @Transactional
    public OrderDetail createOrderDetail(OrderDetailDTO newOrderDetail) {
        Order order = orderRepository.findById(newOrderDetail.getOrderId())
                .orElseThrow(() -> new DataNotFoundException("Order Not Found"));
        Product product = productRepository.findById(newOrderDetail.getProductId())
                .orElseThrow(() -> new DataNotFoundException("Product Not Found"));
        OrderDetail orderDetail = OrderDetail.builder()
                .orderId(order)
                .product(product)
                .price(newOrderDetail.getPrice())
                .numberOfProducts(newOrderDetail.getNumberOfProducts())
                .totalMoney(newOrderDetail.getTotalMoney())
                .build();
        return orderDetailRepository.save(orderDetail);
    }

    @Override
    public OrderDetail getOrderDetail(long id) {
        return orderDetailRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Order Not Found"));
    }

    @Override
    @Transactional
    public OrderDetail updateOrderDetail(long id, OrderDetailDTO updateOrderDetail) {
        OrderDetail existingOrderDetail = orderDetailRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("OrderDetail Not Found"));
        Order existingOrder = orderRepository.findById(updateOrderDetail.getOrderId())
                .orElseThrow(() -> new DataNotFoundException("Order Not Found"));
        Product product = productRepository.findById(updateOrderDetail.getProductId())
                .orElseThrow(() -> new DataNotFoundException("Product Not Found"));
        existingOrderDetail.setProduct(product);
        existingOrderDetail.setPrice(updateOrderDetail.getPrice());
        existingOrderDetail.setNumberOfProducts(updateOrderDetail.getNumberOfProducts());
        existingOrderDetail.setTotalMoney(updateOrderDetail.getTotalMoney());
        return orderDetailRepository.save(existingOrderDetail);
    }

    @Override
    @Transactional
    public void deleteOrderDetail(long orderDetailId) {
        orderDetailRepository.deleteById(orderDetailId);
    }

    @Override
    public List<OrderDetail> getAllOrderDetails(long orderId) {
        return orderDetailRepository.findByOrderId(orderId);
    }
}
