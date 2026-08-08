package ru.andderv.order.TeacherOrdersApp.models;

import lombok.*;

import java.math.BigDecimal;

/**
 * @author andderV
 * @version 22.04.2025 18:27
 * TeacherOrdersApp
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class ResultOrder {
    private String providerName;
    private String contractName;
    private String productName;
    private String unitName;
    private BigDecimal price;
    private BigDecimal quantityBudget;
    private BigDecimal quantityOffBudget;
}
