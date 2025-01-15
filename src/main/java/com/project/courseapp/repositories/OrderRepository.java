package com.project.courseapp.repositories;

import com.project.courseapp.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByOrderId(Long orderId);
}
