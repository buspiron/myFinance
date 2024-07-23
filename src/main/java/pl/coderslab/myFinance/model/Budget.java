package pl.coderslab.myFinance.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jdk.dynalink.linker.LinkerServices;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "budgets")
public class Budget {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, length = 100)
    @NotEmpty(message = "Name cannot be empty.")
    @Size(max = 100, message = "Name cannot be longer than 100 characters.")
    private String name;

    @Column(name = "total_amount", precision = 10, scale = 2)
    @DecimalMin(value = "0.00", message = "Total amount must be positive.")
    private BigDecimal totalAmount;

    @Column(name = "start_date")
    @NotNull(message = "Start date cannot be null.")
    private LocalDate startDate;

    @Column(name = "end_date")
    @NotNull(message = "End date cannot be null.")
    private LocalDate endDate;

    @OneToMany(mappedBy = "budget", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BudgetItem> budgetItems;



}