package com.example.productcategoryservice.mapper;

import com.example.productcategoryservice.dto.CategoryRequestDto;
import com.example.productcategoryservice.dto.CategoryResponseDto;
import com.example.productcategoryservice.model.Category;

import java.util.List;

public interface CategoryMapper {
    Category map(CategoryRequestDto categoryRequestDto);

    CategoryResponseDto map(Category category);

    List<CategoryResponseDto> map(List<Category> categoryList);
}
