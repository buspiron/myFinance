package pl.coderslab.myFinance.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;


@Data
@AllArgsConstructor
public class BudgetChartData {
    private String name;
    private BigDecimal totalAmount;
    private BigDecimal usedAmount;
}