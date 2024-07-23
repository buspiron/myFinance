package pl.coderslab.myFinance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.myFinance.model.BudgetItem;

import java.util.List;

public interface BudgetItemRepository extends JpaRepository<BudgetItem, Integer> {
    List<BudgetItem> findByBudgetId(Integer budgetId);
    List<BudgetItem> findByBudgetIdIn(List<Integer> budgetIds);

}