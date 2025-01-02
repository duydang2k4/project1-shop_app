package com.project.courseapp.Controller;

import com.project.courseapp.dtos.OrderDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/orders")
public class OrderController {
    @PostMapping("")
    public ResponseEntity<?> createOrder(@RequestBody @Valid OrderDTO order, BindingResult result){
        try{
            if(result.hasErrors()){
                List<String> errorMessages = result.getFieldErrors().stream().map(error -> error.getDefaultMessage()).toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }
            return ResponseEntity.ok("Create order successfully");
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/{user_id}")
    public ResponseEntity<?> getOrders(@Valid @PathVariable("user_id") Long userId){
        try {
            return ResponseEntity.ok(String.format("Get orders of user with id = %d successfully", userId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrder(@Valid @PathVariable Long id, @Valid @RequestBody OrderDTO order){
        try {
            return ResponseEntity.ok(String.format("Update order with id = %d successfully", id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @DeleteMapping("/{id}") //Cập nhật active = 0
    public ResponseEntity<?> deleteOrder(@Valid @PathVariable Long id){
        try {
            return ResponseEntity.ok(String.format("Delete order with id = %d successfully", id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}