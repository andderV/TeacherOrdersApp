package ru.andderv.order.TeacherOrdersApp.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

/**
 * @author andderV
 * @version 28.10.2024 8:59
 * TeacherOrdersApp
 */
@Entity()
@Table(name = "groups_of_mu")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class GroupOfMU {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_id")
    private int id;

    @Column(name = "group_name", nullable = false, unique = true)
    @NotEmpty(message = "Поле не может быть пустым")
    @Size(min = 3, max = 10, message = "Название должно быть не менее 3 и не более 10 символов")
    private String groupName;

    @ToString.Exclude
    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
    private List<MeasureUnit> units;
}
