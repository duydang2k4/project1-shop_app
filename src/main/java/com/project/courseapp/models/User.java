package com.project.courseapp.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "user")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100)
    private String fullname;

    @Column(name = "phonenumber", nullable = false)
    private String phoneNumber;

    @Column(length = 200, nullable = false)
    private String address;

    @Column(length = 100, nullable = false)
    private String password;

    @Column(name = "is_active")
    private boolean isActive;

    @Column(name = "date_of-birth")
    private Date dateOfBirth;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;
}
