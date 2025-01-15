package com.project.courseapp.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private String fullname;

    @NotBlank(message = "Phonenumber is required")
    private String phonenumber;

    @NotBlank(message = "Address is required")
    private String address;

    @NotBlank(message = "Password is required")
    private String password;

    private String retypePassword;

    private Date date_of_birth;

    private int facebook_account_id;

    private int google_account_id;

    @NotNull(message = "Required")
    private Long role_id;
}
