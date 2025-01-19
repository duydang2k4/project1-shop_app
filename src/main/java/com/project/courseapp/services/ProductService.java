package com.project.courseapp.services;

import com.project.courseapp.dtos.ProductDTO;
import com.project.courseapp.execeptions.DataNotFoundException;
import com.project.courseapp.models.Category;
import com.project.courseapp.models.Product;
import com.project.courseapp.repositories.CategoryRepository;
import com.project.courseapp.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ProductService implements iProductService{
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Override
    @Transactional
    public Product createProduct(ProductDTO productDTO) throws DataNotFoundException {
        Category existingCategory = categoryRepository.findById(productDTO.getCategory_id())
                .orElseThrow(() -> new DataNotFoundException("Category not found"));
        Product newProduct = Product.builder()
                .name(productDTO.getName())
                .price(productDTO.getPrice())
                .thumbnail(productDTO.getThumbnail())
                .description(productDTO.getDescription())
                .categoryId(existingCategory)
                .build();
        return productRepository.save(newProduct);
    }

    @Override
    public Product getProductById(long id) throws Exception {
        return productRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Product not found"));
    }

    @Override
    public Page<Product> getAllProducts(String keyword, Long categoryId, PageRequest pageRequest) {
        // Lấy danh sách sản phẩm theo trang (page), giới hạn (limit), và categoryId (nếu có)
        Page<Product> productsPage;
        productsPage = productRepository.searchProducts(categoryId, keyword, pageRequest);
        return productsPage.map(product -> product);
    }
//    public Page<Product> getAllProducts(PageRequest pageRequest) {
//        return productRepository.findAll(pageRequest);
//    }

    @Override
    @Transactional
    public Product updateProduct(long id, ProductDTO productDTO) throws Exception{
            Product existingProduct = getProductById(id);
            if(existingProduct != null) {
                Category existingCategory = categoryRepository.findById(productDTO.getCategory_id())
                        .orElseThrow(() -> new DataNotFoundException("Category not found"));
                existingProduct.setCategoryId(existingCategory);
                existingProduct.setName(productDTO.getName());
                existingProduct.setPrice(productDTO.getPrice());
                existingProduct.setThumbnail(productDTO.getThumbnail());
                existingProduct.setDescription(productDTO.getDescription());
                return productRepository.save(existingProduct);
            }
        return null;
    }

    @Override
    @Transactional
    public void deleteProduct(long id) {
        Optional<Product> productOptional = productRepository.findById(id);
        productOptional.ifPresent(productRepository::delete);
    }

    @Override
    public boolean isProductExistByName(String productName) {
        return productRepository.existsByName(productName);
    }
}
