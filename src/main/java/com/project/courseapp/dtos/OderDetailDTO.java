package com.project.courseapp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OderDetailDTO {
    @JsonProperty("order_id")
    @Min(value = 1, message = "Order ID > 0")
    private Long orderId;

    @Min(value = 1, message = "Product ID > 0")
    private Long product_id;

    @Min(value = 1, message = "Price > 0")
    private Float price;

    @Min(value = 1, message = "Min = 1")
    private Long number_of_products;

    private Float total_money;
}
