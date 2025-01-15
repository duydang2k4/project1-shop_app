package com.project.courseapp.services;

import com.project.courseapp.dtos.UserDTO;
import com.project.courseapp.execeptions.DataNotFoundException;
import com.project.courseapp.models.Role;
import com.project.courseapp.models.User;
import com.project.courseapp.repositories.RoleRepository;
import com.project.courseapp.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService implements iUserService{
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    @Override
    public User creatUser(UserDTO userDTO) {
        String phoneNumber = userDTO.getPhonenumber();
        if(userRepository.existsByPhoneNumber(phoneNumber)){
            throw new DataIntegrityViolationException("Phone number already exists");
        }
        User newUser = User.builder()
                .fullname(userDTO.getFullname())
                .phoneNumber(userDTO.getPhonenumber())
                .address(userDTO.getAddress())
                .password(userDTO.getPassword())
                .dateOfBirth(userDTO.getDate_of_birth())
                .build();
        Role role= roleRepository.findById(userDTO.getRole_id())
                .orElseThrow(()->new DataNotFoundException("Role not found"));
        newUser.setRole(role);
        return userRepository.save(newUser);
    }

    @Override
    public String login(String phoneNumber, String password) {
        return null;
    }
}
