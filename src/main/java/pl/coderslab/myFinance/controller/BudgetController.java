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
import pl.coderslab.myFinance.model.User;
import pl.coderslab.myFinance.service.BudgetItemService;
import pl.coderslab.myFinance.service.BudgetService;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/budgets")
public class BudgetController {

    private final BudgetService budgetService;
    private final BudgetItemService budgetItemService;


    public BudgetController(BudgetService budgetService, BudgetItemService budgetItemService) {
        this.budgetService = budgetService;
        this.budgetItemService = budgetItemService;
    }

    @GetMapping
    public String listBudgets(Model model, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId != null) {
            model.addAttribute("budgets", budgetService.findByUserId(userId));
        }
        return "budgets/list";
    }

    @GetMapping("/add")
    public String addBudgetForm(Model model) {
        model.addAttribute("budget", new Budget());
        return "budgets/add";
    }

    @PostMapping("/add")
    public String addBudget(@Valid @ModelAttribute Budget budget, BindingResult result, HttpSession session) {
        if (result.hasErrors()) {
            return "budgets/add";
        }
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId != null) {
            User user = new User();
            user.setId(userId);
            budget.setUser(user);
            budgetService.save(budget);
        }
        return "redirect:/budgets";
    }

    @GetMapping("/edit/{id}")
    public String editBudgetForm(@PathVariable Integer id, Model model) {
        Budget budget = budgetService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid budget Id:" + id));
        model.addAttribute("budget", budget);
        return "budgets/edit";
    }

    @PostMapping("/edit/{id}")
    public String editBudget(@PathVariable Integer id, @Valid @ModelAttribute Budget budget, BindingResult result) {
        if (result.hasErrors()) {
            return "budgets/edit";
        }
        budget.setId(id);
        budgetService.save(budget);
        return "redirect:/budgets";
    }

    @GetMapping("/delete/{id}")
    public String deleteBudget(@PathVariable Integer id) {
        budgetService.deleteById(id);
        return "redirect:/budgets";
    }

    @GetMapping("/details/{id}")
    public String budgetDetails(@PathVariable Integer id, Model model) {
        Optional<Budget> budgetOpt = budgetService.findById(id);
        if (budgetOpt.isPresent()) {
            Budget budget = budgetOpt.get();
            List<BudgetItem> budgetItems = budgetItemService.findByBudgetId(id);

            model.addAttribute("budget", budget);
            model.addAttribute("budgetItems", budgetItems);
            model.addAttribute("usedAmount", budgetService.getUsedAmountByBudgetId(id));
        } else {
            model.addAttribute("error", "Budget not found");
        }
        return "budgets/details";
    }
}
