package pl.coderslab.myFinance.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.coderslab.myFinance.model.BudgetItem;
import pl.coderslab.myFinance.repository.BudgetItemRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class BudgetItemService {

    private final BudgetItemRepository budgetItemRepository;

    @Autowired
    public BudgetItemService(BudgetItemRepository budgetItemRepository) {
        this.budgetItemRepository = budgetItemRepository;
    }

    public Optional<BudgetItem> findById(Integer id) {
        return budgetItemRepository.findById(id);
    }

    public BudgetItem save(BudgetItem budgetItem) {
        return budgetItemRepository.save(budgetItem);
    }

    public void deleteById(Integer id) {
        budgetItemRepository.deleteById(id);
    }

    public List<BudgetItem> findByBudgetIds(List<Integer> budgetIds) {
        return budgetItemRepository.findByBudgetIdIn(budgetIds);
    }

    public List<BudgetItem> findByBudgetId(Integer budgetId) {
        return budgetItemRepository.findByBudgetId(budgetId);
    }

    public BigDecimal getTotalAllocatedAmountByBudgetId(Integer budgetId) {
        List<BudgetItem> items = budgetItemRepository.findByBudgetId(budgetId);
        return items.stream()
                .map(BudgetItem::getAllocatedAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}