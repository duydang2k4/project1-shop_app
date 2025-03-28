package com.project.courseapp.services;

import com.project.courseapp.dtos.ProductDTO;
import com.project.courseapp.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

public interface iProductService {
    public Product createProduct(ProductDTO productDTO);

    public Product getProductById(long id) throws Exception;

    Page<Product> getAllProducts(String keyword, Long categoryId,PageRequest pageRequest);

    Product updateProduct(long id, ProductDTO productDTO) throws Exception;

    void deleteProduct(long id);

    boolean isProductExistByName(String productName);
}
