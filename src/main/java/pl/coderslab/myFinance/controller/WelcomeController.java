package pl.coderslab.myFinance.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.coderslab.myFinance.dto.BudgetChartData;
import pl.coderslab.myFinance.model.Budget;
import pl.coderslab.myFinance.service.BudgetService;
import pl.coderslab.myFinance.service.UserService;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class WelcomeController {

    private final UserService userService;
    private final BudgetService budgetService;

    public WelcomeController(UserService userService, BudgetService budgetService) {
        this.userService = userService;
        this.budgetService = budgetService;
    }


    @GetMapping("/welcome")
    public String welcome(Model model, HttpSession session) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        var user = userService.getUserByUsername(username);

        if (user.isPresent()) {
            Integer userId = user.get().getId();
            model.addAttribute("username", username);
            model.addAttribute("userId", userId);
            session.setAttribute("userId", userId);
            List<Budget> budgets = budgetService.findByUserId(userId);
            List<BudgetChartData> chartData = budgets.stream()
                    .map(budget -> {
                        BigDecimal usedAmount = budgetService.getUsedAmountByBudgetId(budget.getId());
                        return new BudgetChartData(
                                budget.getName(),
                                budget.getTotalAmount(),
                                usedAmount
                        );
                    })
                    .collect(Collectors.toList());
            model.addAttribute("chartData", chartData);
        } else {
            model.addAttribute("username", "Unknown");
            model.addAttribute("userId", "N/A");
        }
        return "welcome";
    }
}