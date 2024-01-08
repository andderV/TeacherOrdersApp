package ru.andderv.order.TeacherOrdersApp.models;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @author andderV
 * @date 29.12.2023 7:53
 * TeacherOrdersApp
 */
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Result{
    private String productName;
    private double quantity;
    private String measureUnitName;
    private int groceryId;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date start;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date end;

    public Result(String productName, double quantity, String measureUnitName, int groceryId) {
        this.productName = productName;
        this.quantity = quantity;
        this.measureUnitName = measureUnitName;
        this.groceryId = groceryId;
    }

    public Result(String productName, double quantity, String measureUnitName, int groceryId, Date start, Date end) {
        this.productName = productName;
        this.quantity = quantity;
        this.measureUnitName = measureUnitName;
        this.groceryId = groceryId;
        this.start = start;
        this.end = end;
    }
}
