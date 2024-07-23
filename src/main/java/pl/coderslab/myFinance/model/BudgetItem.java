package pl.coderslab.myFinance.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "budget_items")
public class BudgetItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "budget_id", nullable = false)
    @NotNull(message = "Budget must not be null")
    private Budget budget;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    @NotNull(message = "Category must not be null")
    private Category category;

    @Column(name = "allocated_amount", precision = 10, scale = 2)
    @DecimalMin(value = "0.00", message = "Allocated amount must be at least 0.00")
    private BigDecimal allocatedAmount;
}