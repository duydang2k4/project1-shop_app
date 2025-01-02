package com.project.courseapp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonAppend;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class OrderDTO {
    @Min(value = 1, message = "User's ID must be > 0")
    private Long user_id;
    public Long getUser_id() {
        return user_id;
    }
    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    private String fullname;
    public String getFullname() {
        return fullname;
    }
    public void setFullname(String fullname) {
        this.fullname = fullname;
    }
    private String email;
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    @NotBlank(message = "Phone number is required")
    @Size(min = 10, message = "It is not a phone number")
    @JsonProperty("phone_number")
    private String phonenumber;
    public String getPhonenumber() {
        return phonenumber;
    }
    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }
    @Min(value = 0, message = "Total money must be >= 0")
    private Float total_money;
    public Float getTotal_money() {
        return total_money;
    }
    public void setTotal_money(Float total_money) {
        this.total_money = total_money;
    }
    private String payment_method;
    public String getPayment_method() {
        return payment_method;
    }
    public void setPayment_method(String payment_method) {
        this.payment_method = payment_method;
    }
}
