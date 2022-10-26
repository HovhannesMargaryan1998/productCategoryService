package com.example.productcategoryservice.service;


import com.example.productcategoryservice.dto.ProductRequestDto;
import com.example.productcategoryservice.dto.ProductResponseDto;
import com.example.productcategoryservice.mapper.ProductMapper;
import com.example.productcategoryservice.model.Product;
import com.example.productcategoryservice.repository.CategoryRepository;
import com.example.productcategoryservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;

    public void save(Product product) {
        productRepository.save(product);
    }


    public List<Product> findAll() {
        return productRepository.findAll();
    }


    public void removeById(int id) {
        productRepository.deleteById(id);
    }


    public Optional<Product> getProductById(int id) {
        return productRepository.findById(id);


    }

    public List<Product> findAllProductByCategoryId(int id) {
        return productRepository.findAllByCategoryId();
    }

    public ProductResponseDto update(int id, ProductRequestDto productRequestDto) {
        Optional<Product> byId = productRepository.findById(id);
        if (byId.isEmpty()) {
            return null;
        }
        Product product = byId.get();
        if (productRequestDto.getTitle() != null) {
            product.setTitle(productRequestDto.getTitle());
        }
        if (productRequestDto.getCount() != 0) {
            product.setCount(productRequestDto.getCount());
        }
        if (productRequestDto.getPrice() != 0) {
            product.setPrice(productRequestDto.getPrice());
        }
        Product save = productRepository.save(product);
        return productMapper.map(save);
    }
}
