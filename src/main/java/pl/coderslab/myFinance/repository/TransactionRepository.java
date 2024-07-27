package pl.coderslab.myFinance.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.coderslab.myFinance.model.Transaction;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    List<Transaction> findByUserId(Integer userId);
    List<Transaction> findByCategoryId(Integer categoryId);

    List<Transaction> findByUserId(Integer userId, Sort sort);

    @Query("SELECT t FROM Transaction t WHERE t.user.id = :userId AND t.category.name = :category")
    List<Transaction> findByUserIdAndCategoryName(@Param("userId") Integer userId, @Param("category") String category, Sort sort);
}