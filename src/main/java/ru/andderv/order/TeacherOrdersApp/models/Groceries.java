package ru.andderv.order.TeacherOrdersApp.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.Cascade;

import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "grocery")
    @Cascade({org.hibernate.annotations.CascadeType.PERSIST,
            org.hibernate.annotations.CascadeType.MERGE})
    private List<GroceryItem> groceriesItem;

    @OneToMany(mappedBy = "product", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    private List<OrdersToSuppliersGrocery> orders;

    @OneToMany(mappedBy = "product", cascade = {CascadeType.ALL})
    private List<ContractsGroceries> contractsGroceries;

    @ToString.Exclude
    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
            name = "groceries_measure_unit",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "measure_unit_id"))
    private List<MeasureUnit> measureUnits = new ArrayList<>();

    @OneToMany(mappedBy = "groceries", cascade = {CascadeType.ALL})
    private List<GroceriesMeasureUnit>groceriesMeasureUnitList;

    @Transient
    private String nameBasicUnit;

    public Groceries(String productName) {
        this.productName = productName;
    }
}
