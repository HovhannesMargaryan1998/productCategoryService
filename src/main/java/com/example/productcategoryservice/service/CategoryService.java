package com.example.productcategoryservice.service;

import com.example.productcategoryservice.dto.CategoryRequestDto;
import com.example.productcategoryservice.dto.CategoryResponseDto;
import com.example.productcategoryservice.mapper.CategoryMapper;
import com.example.productcategoryservice.model.Category;
import com.example.productcategoryservice.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public void save(Category category) {
        categoryRepository.save(category);
    }


    public List<Category> findAll() {
        return categoryRepository.findAll();
    }


    public void removeById(int id) {
        categoryRepository.deleteById(id);
    }


    public Optional<Category> getCategoryById(int id) {
        if (!categoryRepository.existsById(id)) {
            return null;
        }
        Optional<Category> category = categoryRepository.findById(id);
        return categoryRepository.findById(id);
    }


    public CategoryResponseDto update(int id, CategoryRequestDto categoryRequestDto) {
        Optional<Category> byId = categoryRepository.findById(id);
        if (byId.isEmpty()) {
            return null;
        }
        Category category = byId.get();
        if (categoryRequestDto.getName() != null) {
            category.setName(categoryRequestDto.getName());
        }
        Category save = categoryRepository.save(category);
        return categoryMapper.map(save);
    }
}
