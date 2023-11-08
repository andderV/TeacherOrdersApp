package ru.andderv.order.TeacherOrdersApp.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

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
public class MeasureUnit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "measure_unit_id")
    private int measureUnitId;
    @NotEmpty(message = "Поле не может быть пустым")
    @Size(min = 1, max = 10, message = "Значение не может быть меньше 1 и больше 10 символов")
    @Column(name = "name_measure_unit")
    private String measureUnitName;


    @OneToMany(mappedBy = "measureUnit", cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.DETACH, CascadeType.REFRESH})
    private List<Groceries> groceriesMeasureUnitList;

    public MeasureUnit(String measureUnit) {
        this.measureUnitName = measureUnit;
    }
}
