package com.project.courseapp.services;

import com.project.courseapp.dtos.OrderDTO;
import com.project.courseapp.execeptions.DataNotFoundException;
import com.project.courseapp.models.Order;
import com.project.courseapp.models.User;
import com.project.courseapp.repositories.OrderRepository;
import com.project.courseapp.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OderService implements iOrderService{
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public Order createOrder(OrderDTO orderDTO) throws DataNotFoundException {
        User user = userRepository.findById(orderDTO.getUser_id())
                .orElseThrow(() -> new DataNotFoundException("User not found"));
        modelMapper.typeMap(OrderDTO.class, Order.class)
                .addMappings(mapper -> mapper.skip(Order::setId));
        Order order = new Order();
        modelMapper.map(orderDTO, order);
        order.setUserId(user);
        order.setOrderDate(new Date());
        order.setStatus("PENDING");
        order.setActive(true);
        return orderRepository.save(order);
    }

    @Override
    public Order getOrder(long id) {
        return null;
    }

    @Override
    @Transactional
    public Order updateOrder(long id, OrderDTO orderDTO) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Order not found"));
        modelMapper.typeMap(OrderDTO.class, Order.class)
                .addMappings(mapper -> {
                    mapper.skip(Order::setId);
                    mapper.skip(Order::setUserId);
                });
        modelMapper.map(orderDTO, order);
        return orderRepository.save(order);
    }

    @Override
    @Transactional
    public void deleteOrder(long id) {
        Order order = orderRepository.findById(id).orElse(null);
        if (order != null) {
            order.setActive(false);
            orderRepository.save(order);
        }
    }

    @Override
    public List<Order> getAllOrders(long userId) {
        return orderRepository.findOrderByUserId(userId);
    }
}
