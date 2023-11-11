package ru.andderv.order.TeacherOrdersApp.models;

import jakarta.persistence.*;
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
    private String userName;
    @Basic
    @Column(name = "user_password")
    private String userPassword;

}
