package com.Library.Library.service;

import com.Library.Library.entity.Category;
import com.Library.Library.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> tumKategorileriGetir() {
        return categoryRepository.findAll();
    }

    public Category kategoriEkle(Category category) {
        return categoryRepository.save(category);
    }
}