package ru.andderv.order.TeacherOrdersApp.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

/**
 * @author andderV
 * @date 12.11.2023 18:04
 * TeacherOrdersApp
 */
@Entity
@Table(name = "groceries_orders")
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class GroceryItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_order_id")
    private int productId;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Groceries grocery;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Orders order;

    @NotNull(message = "Количество не должно быть 0")
    @Positive(message = "Количество должно быть положительным числом")
    @Column(name = "quantity")
    private float quantity;

}
