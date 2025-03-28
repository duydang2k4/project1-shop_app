package com.project.courseapp.Controller;

import com.project.courseapp.dtos.UserDTO;
import com.project.courseapp.dtos.UserLoginDTO;
import com.project.courseapp.services.iUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("${api.prefix}/users")
@RequiredArgsConstructor
public class UserController {
    private final iUserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> creatUser(@Valid @RequestBody UserDTO userDTO, BindingResult result){
        try{
            if (result.hasErrors()) {
                List<String> errorMessages = result.getFieldErrors().stream().map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }
            if(!userDTO.getPassword().equals(userDTO.getRetypePassword())){
                return ResponseEntity.badRequest().body("Passwords do not match");
            }
            userService.creatUser(userDTO);
            return ResponseEntity.ok(Map.of("message", "User registered successfully"));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@Valid @RequestBody UserLoginDTO userLoginDTO){
        try{
            String token = userService.login(userLoginDTO.getPhonenumber(), userLoginDTO.getPassword(), userLoginDTO.getRole());
            return ResponseEntity.ok(Map.of("token", token));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
