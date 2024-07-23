package pl.coderslab.myFinance.service;


import org.springframework.stereotype.Service;
import pl.coderslab.myFinance.model.Category;
import pl.coderslab.myFinance.repository.CategoryRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public List<Category> findByUserId(Integer userId) {
        return categoryRepository.findByUserId(userId);
    }

    public Optional<Category> findById(Integer id) {
        return categoryRepository.findById(id);
    }

    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    public void deleteById(Integer id) {
        categoryRepository.deleteById(id);
    }
}
