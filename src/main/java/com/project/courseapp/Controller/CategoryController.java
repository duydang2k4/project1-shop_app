package com.project.courseapp.Controller;

import com.project.courseapp.dtos.CategoryDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/categories") //http://localhost:8088/api/v1/categories

public class CategoryController {
    //Hiển thị tất cả categories
    @GetMapping("")
    public ResponseEntity<String> getAllCategories(
            @RequestParam("page") int page,
            @RequestParam("limit") int limit
    ) {
        return ResponseEntity.ok("Get all categories successfully");
    }

    @PostMapping("")
    public ResponseEntity<?> insertCategory(@Valid @RequestBody CategoryDTO categoryDTO, BindingResult result) {
        try {
            if (result.hasErrors()) {
                List<String> errorMessages = result.getFieldErrors().stream().map(FieldError::getDefaultMessage).toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }
            return ResponseEntity.ok("Inserted category successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateCategory(@PathVariable Long id) {
        return ResponseEntity.ok("Update category");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id) {
        return ResponseEntity.ok("Delete category");
    }
}
