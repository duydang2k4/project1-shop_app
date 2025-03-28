package com.project.courseapp.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginDTO {
    @NotBlank(message = "Required")
    private String phonenumber;

    @NotBlank(message = "Required")
    private String password;

    private Long role;
}
