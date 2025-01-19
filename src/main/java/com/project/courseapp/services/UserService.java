package com.project.courseapp.services;

import com.project.courseapp.components.JwtTokenUtil;
import com.project.courseapp.dtos.UserDTO;
import com.project.courseapp.execeptions.DataNotFoundException;
import com.project.courseapp.execeptions.PermissionDenyException;
import com.project.courseapp.models.Role;
import com.project.courseapp.models.User;
import com.project.courseapp.repositories.RoleRepository;
import com.project.courseapp.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService implements iUserService{
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;

    @Override
    @Transactional
    public User creatUser(UserDTO userDTO) throws Exception{
        String phoneNumber = userDTO.getPhonenumber();
        if(userRepository.existsByPhoneNumber(phoneNumber)){
            throw new DataIntegrityViolationException("Phone number already exists");
        }
        Role role= roleRepository.findById(userDTO.getRole_id())
                .orElseThrow(()->new DataNotFoundException("Role not found"));
        if(role.getName().equalsIgnoreCase("ADMIN")){
            throw new PermissionDenyException("You can't add admin role");
        }
        User newUser = User.builder()
                .fullname(userDTO.getFullname())
                .phoneNumber(userDTO.getPhonenumber())
                .address(userDTO.getAddress())
                .password(userDTO.getPassword())
                .dateOfBirth(userDTO.getDate_of_birth())
                .build();

        newUser.setRole(role);
        if (userDTO.getFacebook_account_id() == 0 && userDTO.getGoogle_account_id() == 0){
            String password = userDTO.getPassword();
            String encodedPassword = passwordEncoder.encode(password);
            newUser.setPassword(encodedPassword);
        }
        return userRepository.save(newUser);
    }

    @Override
    public String login(String phoneNumber, String password, Long role_id) {
        Optional<User> optionalUser = userRepository.findByPhoneNumber(phoneNumber);
        if(optionalUser.isEmpty()){
            throw new DataNotFoundException("Invalid phone number or password");
        }
        User existingUser = optionalUser.get();
        if(!existingUser.getRole().getId().equals(role_id)){
            throw new DataNotFoundException("Invalid role");
        }
        //Check password
        if(!passwordEncoder.matches(password, existingUser.getPassword())){
            throw new BadCredentialsException("Invalid password");
        }
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(existingUser
                .getPhoneNumber(), password, existingUser.getAuthorities());
        //authenticate with Java Spring Security
        authenticationManager.authenticate(authenticationToken);
        return jwtTokenUtil.generateToken(existingUser);
    }
}
