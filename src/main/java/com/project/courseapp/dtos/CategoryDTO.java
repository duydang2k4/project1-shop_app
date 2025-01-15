package com.project.courseapp.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class CategoryDTO {
    @NotBlank(message = "Not empty")
    private String name;
}
