package com.project.courseapp.Controller;

import com.project.courseapp.dtos.OderDetailDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/order_details")
public class OrderDetailController {
    @PostMapping("")
    public ResponseEntity<?> creatOrderDetail(@Valid @RequestBody OderDetailDTO newOrderDetail){
        return ResponseEntity.ok("Creat order detail");
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderDetail(@Valid @PathVariable("id") Long id){
        return ResponseEntity.ok("Order detail with id: " + id);
    }
    //lấy danh sách chi tiết của 1 order
    @GetMapping("/order/{orderId}")
    public ResponseEntity<?> getOrderDetails(@Valid @PathVariable("orderId") Long orderId){
        return ResponseEntity.ok("Order details of order with id: " + orderId);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrderDetail(@Valid @PathVariable("id") Long id, @RequestBody OderDetailDTO newOrderDetailData){
        return ResponseEntity.ok("Update order detail with id: " + id + ",new: " + newOrderDetailData.toString());
    }
    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteOrderDetail(@PathVariable("id") Long id){
        return ResponseEntity.noContent().build();
    }
}
