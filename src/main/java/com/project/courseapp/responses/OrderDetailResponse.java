package com.project.courseapp.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.courseapp.models.Order;
import com.project.courseapp.models.OrderDetail;
import com.project.courseapp.models.Product;
import com.project.courseapp.models.User;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDetailResponse {
    private Long id;

    @JsonProperty("order_id")
    private Long orderId;

    @JsonProperty("product_id")
    private Long productId;

    private Float price;

    @JsonProperty("number_of_products")
    private int numberOfProducts;

    @JsonProperty("total_money")
    private Float totalMoney;

    public static OrderDetailResponse fromOrderDetail(OrderDetail orderDetail) {
        return OrderDetailResponse.builder()
                .id(orderDetail.getId())
                .orderId(orderDetail.getOrderId().getId())
                .productId(orderDetail.getProduct().getId())
                .price(orderDetail.getPrice())
                .numberOfProducts(orderDetail.getNumberOfProducts())
                .totalMoney(orderDetail.getTotalMoney())
                .build();
    }
}
