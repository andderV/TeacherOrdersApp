package ru.andderv.order.TeacherOrdersApp.models;

import jakarta.persistence.*;
import lombok.Data;

/**
 * @author andderV
 * @version 13.11.2024 12:35
 * TeacherOrdersApp
 */
@Entity
@Table(name = "groceries_measure_unit")
@Data
public class GroceriesMeasureUnit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "measure_unit_id", referencedColumnName = "measure_unit_id")
    private MeasureUnit measureUnit;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "product_id")
    private Groceries groceries;

}
