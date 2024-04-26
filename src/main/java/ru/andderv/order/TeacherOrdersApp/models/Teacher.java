package ru.andderv.order.TeacherOrdersApp.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
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
    @Pattern(regexp = "^\\+7\\d{10}", message = "Номер телефона должен быть в формате +7ХХХХХХХХХХ")
    @Column(name = "phone_number")
    private String phoneNumber;
    @OneToMany(mappedBy = "owner", cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.DETACH, CascadeType.REFRESH})
    private List<Orders> ordersList;
    @Basic
    @Column(name = "user_name")
    @NotEmpty(message = "Имя не должно быть пустым")
    @Size(min = 2, max = 100, message = "Имя не должно быть меньше 2 и больше 100 символов")
    private String userName;
    @Basic
    @Column(name = "user_password")
    @NotEmpty(message = "Пароль не должен быть пустым")
    private String userPassword;
    @Transient
    private String confirmUserPassword;
    @Column(name = "role")
    private String role;
    @Transient
    private String key;

    public Teacher(String teacherName, String email, String phoneNumber, String userName, String userPassword, String role) {
        this.teacherName = teacherName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.userName = userName;
        this.userPassword = userPassword;
        this.role = role;
    }
}
