package ru.andderv.order.TeacherOrdersApp.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;


/**
 * @author andderV
 * @date 28.10.2023 23:09
 * TeacherOrdersApp
 */
@Entity
@Table(name = "orders")
@Getter
@Setter
//@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private int orderId;
    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @FutureOrPresent(message = "Дата должна быть не позднее текущей даты ")
    private Date dateOrder;
    @ManyToOne
    @JoinColumn(name = "teacher_id", referencedColumnName = "teacher_id")
    private Teacher owner;

    @OneToMany(mappedBy = "order")
    private List<GroceryItem>groceriesItem;

    @ManyToMany(mappedBy = "ordersGroceriesList", cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.DETACH, CascadeType.REFRESH})
    private List<Groceries>ordersGroseriesList;

    public Orders(Date dateOrder) {
        this.dateOrder = dateOrder;
    }
}
