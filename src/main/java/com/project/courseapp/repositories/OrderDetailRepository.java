package com.project.courseapp.repositories;

import com.project.courseapp.models.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
    @Query("SELECT o FROM OrderDetail o WHERE o.orderId.id = :orderId")
    List<OrderDetail> findByOrderId(@Param("orderId") Long orderId);

}
