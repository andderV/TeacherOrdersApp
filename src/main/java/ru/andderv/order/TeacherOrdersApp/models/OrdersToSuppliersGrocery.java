package ru.andderv.order.TeacherOrdersApp.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "orders_to_suppliers_groceries")
public class OrdersToSuppliersGrocery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", referencedColumnName = "order_id", nullable = false)
    private OrdersToSupplier order;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Groceries product;

    @Column(name = "quantity_budget", precision = 4, scale = 2)
    @Positive(message = "Количество не может быть меньше или равно 0")
    private BigDecimal quantityBudget;

    @Column(name = "quantity_offbudget", precision = 4, scale = 2)
    @Positive(message = "Количество не может быть меньше или равно 0")
    private BigDecimal quantityOffBudget;

}