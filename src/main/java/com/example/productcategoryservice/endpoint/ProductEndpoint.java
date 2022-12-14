package com.example.productcategoryservice.endpoint;


import com.example.productcategoryservice.dto.ProductRequestDto;
import com.example.productcategoryservice.dto.ProductResponseDto;
import com.example.productcategoryservice.mapper.ProductMapper;
import com.example.productcategoryservice.model.Category;
import com.example.productcategoryservice.model.Product;
import com.example.productcategoryservice.service.CategoryService;
import com.example.productcategoryservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class ProductEndpoint {

    private final ProductService productService;
    private final ProductMapper productMapper;
    private final CategoryService categoryService;

    @GetMapping("/products")
    public ResponseEntity<List<ProductResponseDto>> getAllProducts() {
        if (productService.findAll().size() == 0) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productMapper.map(productService.findAll()));
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<ProductResponseDto> getProductById(@PathVariable("id") int id) {
        Optional<Product> productById = productService.getProductById(id);
        if (productById.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productMapper.map(productById.get()));
    }

    @PutMapping("/products/{id}")
    ResponseEntity<ProductResponseDto> updateProduct(@PathVariable("id") int id, @RequestBody ProductRequestDto productRequestDto) {
        ProductResponseDto update = productService.update(id, productRequestDto);
        if (update == null) {
            ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(update);
    }


    @DeleteMapping("/products/{id}")
    ResponseEntity<Product> deleteProductById(@PathVariable("id") int id) {
        Optional<Product> productById = productService.getProductById(id);
        if (productById.isPresent()) {
            return ResponseEntity.badRequest().build();
        }
        productService.removeById(productById.get());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/products")
    public ResponseEntity<?> createProduct(@RequestBody ProductRequestDto productRequestDto) {
        productService.save(productMapper.map(productRequestDto));
        return ResponseEntity.ok(productRequestDto);
    }

    @GetMapping("/products/{categoryId}")
    ResponseEntity<List<ProductResponseDto>> getByCategoryId(@PathVariable("id") int id) {
        Optional<Category> byId = categoryService.getCategoryById(id);
        if (byId.isEmpty()) {
            return ResponseEntity.badRequest().build();        }
        return ResponseEntity.ok(productMapper.map(productService.findAllProductByCategoryId(id)));
    }


}