package ru.andderv.order.TeacherOrdersApp.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * @author andderV
 * @date 11.11.2023 15:47
 * TeacherOrdersApp
 */
@Entity
@Table(name = "security")
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class SecurityPerson {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "security_id")
    private int securityId;
    @Basic
    @Column(name = "user_name")
    @NotEmpty(message = "Имя не должно быть пустым")
    @Size(min = 2, max = 100, message = "Имя не должно быть меньше 2 и больше 100 символов")
    private String userName;
    @Basic
    @Column(name = "user_password")
    @NotEmpty(message = "Пароль не должен быть пустым")
    private String userPassword;
    @Column(name = "role")
    private String role;

}
