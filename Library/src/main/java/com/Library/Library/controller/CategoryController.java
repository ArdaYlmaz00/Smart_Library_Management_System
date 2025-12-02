package com.Library.Library.controller;

import com.Library.Library.entity.Category;
import com.Library.Library.service.CategoryService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public List<Category> getCategories() {
        return categoryService.tumKategorileriGetir();
    }

    @PostMapping
    public Category addCategory(@RequestBody Category category) {
        return categoryService.kategoriEkle(category);
    }
}