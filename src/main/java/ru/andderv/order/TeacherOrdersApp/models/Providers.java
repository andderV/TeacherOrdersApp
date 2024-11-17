package ru.andderv.order.TeacherOrdersApp.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

/**
 * @author andderV
 * @version 25.08.2024 18:28
 * TeacherOrdersApp
 */
@Entity
@Table(name = "providers")
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class Providers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "provider_id")
    private int providerId;

    @Column(name = "provider_name", nullable = false, unique = true)
    @NotEmpty(message = "Поле не должно быть пустым")
    @Size(min = 1, max = 50, message = "Название поставщика не может быть меньше 1 и больше 50 символов")
    private String providerName;

    @Column(name = "contact_person", nullable = false)
    @NotEmpty(message = "Поле не может быть пустым")
    private String contactPerson;

    @Column(name = "phone_number", nullable = false)
    @NotEmpty(message = "Поле не может быть пустым")
    @Pattern(regexp = "^\\+7\\d{10}", message = "Номер телефона должен быть в формате +7ХХХХХХХХХХ")
    private String phoneNumber;

    @Column(name = "email", nullable = false)
    @NotEmpty(message = "Поле не может быть пустым")
    @Email(message = "Указан не существующий адрес электронной почты")
    private String email;

    @OneToMany(mappedBy = "provider", cascade = {CascadeType.ALL})
    List<Contracts> contracts;

    @OneToMany(mappedBy = "provider", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    List<OrdersToSupplier> ordersToSuppliers;
}
