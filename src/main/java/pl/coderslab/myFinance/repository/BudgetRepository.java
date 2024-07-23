package pl.coderslab.myFinance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.myFinance.model.Budget;
import pl.coderslab.myFinance.model.BudgetItem;

import java.util.List;

public interface BudgetRepository extends JpaRepository<Budget, Integer> {
    List<Budget> findByUserId(Integer userId);

}