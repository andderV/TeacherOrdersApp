package ru.andderv.order.TeacherOrdersApp.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "contracts_groceries")
public class ContractsGroceries {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contract_id", referencedColumnName = "contract_id", nullable = false)
    private Contracts contract;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", referencedColumnName = "product_id", nullable = false)
    private Groceries product;

    @NotNull
    @Column(name = "measure_unit_id")
    private int measureUnitId;

    @NotNull
    @Column(name = "quantity_budget", nullable = false, precision = 4, scale = 2)
    private BigDecimal quantityBudget;

    @NotNull
    @Column(name = "quantity_offbudget", nullable = false, precision = 4, scale = 2)
    private BigDecimal quantityOffBudget;

    @NotNull
    @Column(name = "price", nullable = false, precision = 6, scale = 2)
    private BigDecimal price;

    @NotNull
    @ColumnDefault("(`quantity_budget` * `price`)")
    @Column(name = "sum_budget", nullable = false, precision = 8, scale = 2)
    private BigDecimal sumBudget;

    @NotNull
    @ColumnDefault("(`quantity_offbudget` * `price`)")
    @Column(name = "sum_offbudget", nullable = false, precision = 8, scale = 2)
    private BigDecimal sumOffBudget;

}