package ru.andderv.order.TeacherOrdersApp.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author andderV
 * @version 10.11.2024 9:53
 * TeacherOrdersApp
 */
@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductWithBasicUnitMeasurement {
    private int productId;

    private String productName;

    private MeasureUnit basicUnit;

}
