package com.project.courseapp.services;

import com.project.courseapp.dtos.UserDTO;
import com.project.courseapp.models.User;

public interface iUserService {
    User creatUser(UserDTO userDTO);
    String login(String phoneNumber, String password);
}
