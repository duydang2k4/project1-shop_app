package com.project.courseapp.Controller;

import com.project.courseapp.dtos.OrderDTO;
import com.project.courseapp.models.Order;
import com.project.courseapp.services.iOrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("${api.prefix}/orders")
@RequiredArgsConstructor
public class OrderController {
    private final iOrderService orderService;

    @PostMapping("")
    public ResponseEntity<?> createOrder(@RequestBody @Valid OrderDTO order, BindingResult result){
        try{
            if(result.hasErrors()){
                List<String> errorMessages = result.getFieldErrors().stream()
                        .map(error -> error.getDefaultMessage()).toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }
            Order newOrder = orderService.createOrder(order);
            return ResponseEntity.ok(Map.of(
                    "message", "Create order successfully",
                    "id", newOrder.getId()));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/user/{user_id}")
    public ResponseEntity<?> getOrders(@Valid @PathVariable("user_id") Long userId){
        try {
            List<Order> oders = orderService.getAllOrders(userId);
            return ResponseEntity.ok(oders);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrder(@Valid @PathVariable Long id, @Valid @RequestBody OrderDTO order){
        try {
            Order updatedOrder = orderService.updateOrder(id, order);
            return ResponseEntity.ok(order);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @DeleteMapping("/{id}") //Cập nhật active = 0
    public ResponseEntity<?> deleteOrder(@Valid @PathVariable Long id){
        try {
            orderService.deleteOrder(id);
            return ResponseEntity.ok(String.format("Delete order with id = %d successfully", id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}