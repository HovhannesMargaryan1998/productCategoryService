package com.example.productcategoryservice.service;


import com.example.productcategoryservice.dto.ProductRequestDto;
import com.example.productcategoryservice.dto.ProductResponseDto;
import com.example.productcategoryservice.mapper.ProductMapper;
import com.example.productcategoryservice.model.Product;
import com.example.productcategoryservice.model.User;
import com.example.productcategoryservice.repository.ProductRepository;
import com.example.productcategoryservice.repository.UserRepository;
import com.example.productcategoryservice.security.CurrentUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final UserRepository userRepository;


    public void save(Product product) {
        productRepository.save(product);
    }


    public List<Product> findAll() {
        return productRepository.findAll();
    }


    public void removeById(Product product) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CurrentUser currentUser = (CurrentUser) authentication.getPrincipal();
        User user = currentUser.getUser();
        if (product.getUser().getId() == user.getId()) {
            productRepository.deleteById(product.getId());
        }
    }


    public Optional<Product> getProductById(int id) {
        return productRepository.findById(id);


    }

    public List<Product> findAllProductByCategoryId(int id) {
        return productRepository.findAllByCategoryId(id);
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
