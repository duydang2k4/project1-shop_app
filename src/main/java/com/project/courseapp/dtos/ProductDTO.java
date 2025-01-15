package com.project.courseapp.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ProductDTO {
    @NotBlank(message = "Name is required")
    @Size(min = 3, max = 200, message = "Title must >= 3 and <= 200 characters")
    private String name;

    @Min(value = 0, message = "giá phải >0")
    private Long price;

    private String thumbnail;

    private String description;

    private Integer category_id;

    private MultipartFile file;

}
