package com.project.courseapp.models;

import jakarta.persistence.*;
import lombok.*;
import java.util.Date;

@Entity
@Table(name = "orders")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userId;

    @Column(name = "fullname", length = 100)
    private String fullName;

    @Column(name = "email", length = 100)
    private String email;

    @Column(name = "phone_number",nullable = false, length = 100)
    private String phoneNumber;

    @Column(name = "status")
    private String status;

    @Column(name="order_date")
    private Date orderDate;

    @Column(name = "total_money")
    private Float totalMoney;

    @Column(name = "payment_method")
    private String paymentMethod;

    @Column(name = "active")
    private Boolean active;//thuộc về admin

}
