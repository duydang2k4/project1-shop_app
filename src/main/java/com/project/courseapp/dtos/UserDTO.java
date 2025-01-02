package com.project.courseapp.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private String fullname;

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }
    @NotBlank(message = "Phonenumber is required")
    private String phonenumber;

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }
    @NotBlank(message = "Address is required")
    private String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    @NotBlank(message = "Password is required")
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private String retypePassword;

    public String getRetypePassword() {
        return retypePassword;
    }

    public void setRetypePassword(String retypePassword) {
        this.retypePassword = retypePassword;
    }

    private String date_of_birth;

    public String getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(String date_of_bird) {
        this.date_of_birth = date_of_bird;
    }
    private int facebook_account_id;

    public int getFacebook_account_id() {
        return facebook_account_id;
    }

    public void setFacebook_account_id(int facebook_account_id) {
        this.facebook_account_id = facebook_account_id;
    }
    private int google_account_id;

    public int getGoogle_account_id() {
        return google_account_id;
    }

    public void setGoogle_account_id(int google_account_id) {
        this.google_account_id = google_account_id;
    }

    @NotNull(message = "Required")
    private Long role_id;

    public Long getRole_id() {
        return role_id;
    }

    public void setRole_id(Long role_id) {
        this.role_id = role_id;
    }

}
