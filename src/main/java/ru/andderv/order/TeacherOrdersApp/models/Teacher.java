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
 * @date 28.10.2023 20:17
 * TeacherOrdersApp
 */
@Entity
@Table(name = "teachers")
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "teacher_id")
    private int teacherId;
    @NotEmpty(message = "Поле не может быть пустым")
    @Size(min = 3, max = 100, message = "ФИО не может быть меньше 3 и больше 100 символов")
    @Column(name = "teacher_name")
    private String teacherName;
    @NotEmpty(message = "Поле не может быть пустым")
    @Email(message = "Указан не существующий адрес электронной почты")
    @Column(name = "email")
    private String email;
    @NotEmpty(message = "Поле не может быть пустым")
    @Pattern(regexp = "^\\+7\\(\\d{3}\\)\\d{3}-\\d{2}-\\d{2}", message = "Номер телефона должен быть в формате +7(ХХХ)ХХХ-ХХ-ХХ")
    @Column(name = "phone_number")
    private String phoneNumber;
    @OneToMany(mappedBy = "owner", cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.DETACH, CascadeType.REFRESH})
    private List<Orders> ordersList;

    public Teacher(String teacherName, String email, String phoneNumber) {
        this.teacherName = teacherName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }
}
