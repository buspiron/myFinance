package pl.coderslab.myFinance.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.myFinance.model.Budget;
import pl.coderslab.myFinance.model.BudgetItem;
import pl.coderslab.myFinance.model.Category;
import pl.coderslab.myFinance.service.BudgetItemService;
import pl.coderslab.myFinance.service.BudgetService;
import pl.coderslab.myFinance.service.CategoryService;

import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping("/budget-items")
public class BudgetItemController {

    @Autowired
    private BudgetItemService budgetItemService;

    @Autowired
    private BudgetService budgetService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/add/{budgetId}")
    public String addBudgetItemForm(@PathVariable Integer budgetId, Model model) {
        Budget budget = budgetService.findById(budgetId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid budget Id:" + budgetId));

        model.addAttribute("budgetItem", new BudgetItem());
        model.addAttribute("budget", budget);
        model.addAttribute("categories", categoryService.findByUserId(budget.getUser().getId()));
        return "budget-items/add";
    }

    @PostMapping("/add")
    public String addBudgetItem(@Valid @ModelAttribute BudgetItem budgetItem, BindingResult result) {
        if (result.hasErrors()) {
            return "budget-items/add";
        }
        budgetItemService.save(budgetItem);
        return "redirect:/budgets/details/" + budgetItem.getBudget().getId();
    }

    @GetMapping("/edit/{id}")
    public String editBudgetItemForm(@PathVariable Integer id, Model model) {
        Optional<BudgetItem> optionalBudgetItem = budgetItemService.findById(id);
        if (optionalBudgetItem.isPresent()) {
            BudgetItem budgetItem = optionalBudgetItem.get();
            List<Budget> budgets = budgetService.findAll();
            List<Category> categories = categoryService.findAll();

            model.addAttribute("budgetItem", budgetItem);
            model.addAttribute("budgets", budgets);
            model.addAttribute("categories", categories);

            return "budget-items/edit";
        } else {
            throw new IllegalArgumentException("Invalid budget item Id:" + id);
        }
    }

    @PostMapping("/edit/{id}")
    public String editBudgetItem(@PathVariable Integer id, @Valid @ModelAttribute BudgetItem budgetItem, BindingResult result, HttpSession session) {
        if (result.hasErrors()) {
            return "budget-items/edit";
        }
        Integer userId = (Integer) session.getAttribute("userId");
        budgetItem.setId(id);
        budgetItemService.save(budgetItem);
        return "redirect:/budgets/details/" + budgetItem.getBudget().getId();
    }
}