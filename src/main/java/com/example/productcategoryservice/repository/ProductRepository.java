package com.example.productcategoryservice.repository;


import com.example.productcategoryservice.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    List<Product> findAllByCategoryId();
}
