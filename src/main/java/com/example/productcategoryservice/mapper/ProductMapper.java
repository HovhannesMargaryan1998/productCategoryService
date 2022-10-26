package com.example.productcategoryservice.mapper;

import com.example.productcategoryservice.dto.ProductRequestDto;
import com.example.productcategoryservice.dto.ProductResponseDto;
import com.example.productcategoryservice.model.Product;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {


    Product map(ProductRequestDto productRequestDto);

    ProductResponseDto map(Product product);

    List<ProductResponseDto> map(List<Product> productList);
}
