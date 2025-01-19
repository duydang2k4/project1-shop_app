package com.project.courseapp.services;

import com.project.courseapp.dtos.UserDTO;
import com.project.courseapp.models.User;

public interface iUserService {
    User creatUser(UserDTO userDTO) throws Exception;
    String login(String phoneNumber, String password, Long role);
}
