package ru.andderv.order.TeacherOrdersApp.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;
import java.util.Optional;

/**
 * @author andderV
 * @date 28.10.2023 23:31
 * TeacherOrdersApp
 */
@Entity
@Table(name = "groceries")
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class Groceries {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private int id;
    @NotEmpty(message = "Поле не должно быть пустым")
    @Size(min = 2, max = 50, message = "Название продукта не может быть меньше 2 и больше 50 символов")
    @Column(name = "product_name")
    private String productName;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinTable(name = "groceries_orders",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "order_id"))
    private List<Orders> ordersGroceriesList;

    @ManyToOne
    @JoinColumn(name = "measure_unit_id", referencedColumnName = "measure_unit_id")
    private MeasureUnit measureUnit;

    public Groceries(String productName) {
        this.productName = productName;
    }
}
