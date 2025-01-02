package com.project.courseapp.Controller;

import com.project.courseapp.dtos.ProductDTO;
import jakarta.validation.Valid;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("${api.prefix}/products")
public class ProductController {
    @GetMapping("")
    public ResponseEntity<String> getProducts(
            @RequestParam("page") int page,
            @RequestParam("limit") int limit
    ) {
        return ResponseEntity.ok("Get products successfully");
    }
    @GetMapping("/{id}")
    public ResponseEntity<String> getProductById(@PathVariable ("id") String productId){
        return  ResponseEntity.ok("Product with id: " + productId);
    }
    @DeleteMapping("/{id}")
    public  ResponseEntity<String> deleteProduct(@PathVariable Long id){
        return  ResponseEntity.ok(String.format("Delete product with id: %d",id));
    }
    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    //POST http://localhost:8088/api/v1/products
    public  ResponseEntity<?> creatProduct(@Valid @RequestBody @ModelAttribute ProductDTO productDTO,
//                                          @RequestPart("file") MultipartFile file,
                                          BindingResult result
    ) {
        try {
            if (result.hasErrors()) {
                List<String> errorMessages = result.getFieldErrors().stream().map(FieldError::getDefaultMessage).toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }
            MultipartFile file = productDTO.getFile();
            if(file != null){
                //Kiểm tra kích thước file
                if(file.getSize() > 10 * 1024 * 1024){
                    return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE)
                            .body("File is too large! Maximum size is 10MB");
                }
                String contentType = file.getContentType();
                if(contentType == null || !contentType.startsWith("image/")){
                    return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body("File must be an image");
                }
                String filename = storeFile(file);

            }
            return ResponseEntity.ok("Product created successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    private String storeFile(@NotNull MultipartFile file) throws IOException{
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        //Thêm UUID vào trước tên file để đảm bảo sự duy nhất
        String uniqueFilename = UUID.randomUUID().toString() + "_" + filename;
        //Đường dẫn đến thư mục lưu file
        java.nio.file.Path uploadDir = Paths.get("uploads");
        //Kiểm tra thư mục uploads đã tồi tại hay chưa
        if(!Files.exists(uploadDir)){
            Files.createDirectories(uploadDir);
        }
        //đường dẫn đầy đủ đến file
        java.nio.file.Path destination = Paths.get(uploadDir.toString(), uniqueFilename);
        //sao chép file vào thư mục đích
        Files.copy(file.getInputStream(),destination, StandardCopyOption.REPLACE_EXISTING);
        return uniqueFilename;
    }
}
