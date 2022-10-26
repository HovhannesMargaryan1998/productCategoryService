package com.example.productcategoryservice.endpoint;


import com.example.productcategoryservice.dto.CategoryRequestDto;
import com.example.productcategoryservice.dto.CategoryResponseDto;
import com.example.productcategoryservice.mapper.CategoryMapper;
import com.example.productcategoryservice.model.Category;
import com.example.productcategoryservice.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class CategoryEndpoint {
    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    @GetMapping("/categories")
    public List<CategoryResponseDto> getAllCategories() {
        return categoryMapper.map(categoryService.findAll());
    }

    @GetMapping("/categories/{id}")
    public ResponseEntity<CategoryResponseDto> getCategoryById(@PathVariable("id") int id) {
        Optional<Category> categoryById = categoryService.getCategoryById(id);
        if (categoryById.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(categoryMapper.map(categoryById.get()));
    }

    //
    @PutMapping("/categories/{id}")
    ResponseEntity<CategoryResponseDto> updateCategory(@PathVariable("id") int id, @RequestBody CategoryRequestDto categoryRequestDto) {
        CategoryResponseDto update = categoryService.update(id, categoryRequestDto);
        if (update == null) {
            ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(update);
    }


    @DeleteMapping("/categories/{id}")
    ResponseEntity<Category> deleteCategoryById(@PathVariable("id") int id) {
        categoryService.removeById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/categories")
    public ResponseEntity<?> createCategory(@RequestBody CategoryRequestDto categoryRequestDto) {
        categoryService.save(categoryMapper.map(categoryRequestDto));
        return ResponseEntity.ok(categoryRequestDto);
    }


}