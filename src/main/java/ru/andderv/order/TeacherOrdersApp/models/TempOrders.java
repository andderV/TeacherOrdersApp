package ru.andderv.order.TeacherOrdersApp.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;

/**
 * @author andderV
 * @version 25.08.2024 19:04
 * TeacherOrdersApp
 */
@Entity
@Table(name = "temp_orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class TempOrders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @NotEmpty(message = "Поле не может быть пустым")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    @Future(message = "Дата не может быть текущей или прошедшей")
    @Column(name = "date_order", nullable = false)
    private LocalDate dateOrder;

    @Column(name = "id_product", nullable = false)
    @NotEmpty(message = "Поле не может быть пустым")
    private int productId;

    @Column(name = "quantity", nullable = false)
    @NotNull(message = "Количество не должно быть 0")
    @Positive(message = "Количество должно быть положительным числом")
    private float quantity;
}
