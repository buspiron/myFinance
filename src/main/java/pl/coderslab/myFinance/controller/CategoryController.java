package pl.coderslab.myFinance.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.myFinance.model.Category;
import pl.coderslab.myFinance.model.User;
import pl.coderslab.myFinance.service.CategoryService;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public String listCategories(Model model, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId != null) {
            model.addAttribute("categories", categoryService.findByUserId(userId));
        }
        return "categories/list";
    }

    @GetMapping("/add")
    public String addCategoryForm(Model model) {
        model.addAttribute("category", new Category());
        return "categories/add";
    }

    @PostMapping("/add")
    public String addCategory(@Valid @ModelAttribute Category category, BindingResult result, HttpSession session) {
        if (result.hasErrors()) {
            return "categories/add";
        }
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId != null) {
            User user = new User();
            user.setId(userId);
            category.setUser(user);
            categoryService.save(category);
        }
        return "redirect:/categories";
    }

    @GetMapping("/edit/{id}")
    public String editCategoryForm(@PathVariable Integer id, Model model) {
        Category category = categoryService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid category Id:" + id));
        model.addAttribute("category", category);
        return "categories/edit";
    }

    @PostMapping("/edit/{id}")
    public String editCategory(@PathVariable Integer id, @Valid @ModelAttribute Category category, BindingResult result) {
        if (result.hasErrors()) {
            return "categories/edit";
        }
        category.setId(id);
        categoryService.save(category);
        return "redirect:/categories";
    }

    @GetMapping("/delete/{id}")
    public String deleteCategory(@PathVariable Integer id) {
        categoryService.deleteById(id);
        return "redirect:/categories";
    }
}