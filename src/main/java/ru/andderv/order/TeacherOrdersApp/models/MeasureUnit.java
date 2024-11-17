package ru.andderv.order.TeacherOrdersApp.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author andderV
 * @date 29.10.2023 8:59
 * TeacherOrdersApp
 */
@Entity
@Table(name = "measure_unit")
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class MeasureUnit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "measure_unit_id")
    private int measureUnitId;

    @NotEmpty(message = "Поле не может быть пустым")
    @Size(min = 1, max = 20, message = "Значение не может быть меньше 1 и больше 20 символов")
    @Column(name = "name_measure_unit")
    private String measureUnitName;

    @NotNull(message = "Поле не может быть 0")
    @Column(name = "ratio")
    private float ratio;

    @ManyToOne()
    @JoinColumn(name = "group_id", referencedColumnName = "group_id")
    private GroupOfMU group;

    @ToString.Exclude
    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "measureUnits", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private List<Groceries> groceries = new ArrayList<>();

    @OneToMany(mappedBy = "measureUnit", cascade = CascadeType.ALL)
    private List<GroceriesMeasureUnit>groceriesMeasureUnitList;

    public MeasureUnit(String measureUnit) {
        this.measureUnitName = measureUnit;
    }
}
