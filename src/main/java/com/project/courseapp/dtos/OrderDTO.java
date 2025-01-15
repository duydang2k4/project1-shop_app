package com.project.courseapp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonAppend;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    @Min(value = 1, message = "User's ID must be > 0")
    private Long user_id;

    private String fullname;

    private String email;

    @NotBlank(message = "Phone number is required")
    @Size(min = 10, message = "It is not a phone number")
    @JsonProperty("phone_number")
    private String phonenumber;

    @Min(value = 0, message = "Total money must be >= 0")
    private Float total_money;

    private String payment_method;
}
