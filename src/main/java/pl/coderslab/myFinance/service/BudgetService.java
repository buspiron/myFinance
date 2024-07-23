package pl.coderslab.myFinance.service;

import org.springframework.stereotype.Service;
import pl.coderslab.myFinance.model.Budget;
import pl.coderslab.myFinance.repository.BudgetRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class BudgetService {

    private final BudgetRepository budgetRepository;
    private final BudgetItemService budgetItemService;

    public BudgetService(BudgetRepository budgetRepository, BudgetItemService budgetItemService) {
        this.budgetRepository = budgetRepository;
        this.budgetItemService = budgetItemService;
    }

    public List<Budget> findAll() {
        return budgetRepository.findAll();
    }
    public List<Budget> findByUserId(Integer userId) {
        return budgetRepository.findByUserId(userId);
    }

    public Optional<Budget> findById(Integer id) {
        return budgetRepository.findById(id);
    }

    public Budget save(Budget budget) {
        return budgetRepository.save(budget);
    }

    public void deleteById(Integer id) {
        budgetRepository.deleteById(id);
    }

    public BigDecimal getUsedAmountByBudgetId(Integer budgetId) {
        return budgetItemService.getTotalAllocatedAmountByBudgetId(budgetId);
    }

}
