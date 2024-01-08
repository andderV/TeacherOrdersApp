package ru.andderv.order.TeacherOrdersApp.models;

import jakarta.validation.constraints.FutureOrPresent;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author andderV
 * @date 07.01.2024 20:59
 * TeacherOrdersApp
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class OrderResult {
    private int orderId;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateOrder;
    private String teacherName;

    public OrderResult(int orderId, Date dateOrder, String teacherName) {
        this.orderId = orderId;
        this.dateOrder = dateOrder;
        this.teacherName = teacherName;
    }
}
