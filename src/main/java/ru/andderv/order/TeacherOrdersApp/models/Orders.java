package ru.andderv.order.TeacherOrdersApp.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
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
    @FutureOrPresent(message = "Дата должна быть не позднее текущей даты")
    private Date dateOrder;
    @ManyToOne
    @JoinColumn(name = "teacher_id", referencedColumnName = "teacher_id")
    private Teacher owner;

    @OneToMany(mappedBy = "order")
    @Cascade({org.hibernate.annotations.CascadeType.PERSIST,
            org.hibernate.annotations.CascadeType.MERGE})
    private List<GroceryItem> groceriesItem;

//    @ManyToMany(mappedBy = "ordersGroceriesList", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
//    private List<Groceries> ordersGroseriesList;
//    @Transient
//    @Temporal(TemporalType.DATE)
//    @DateTimeFormat(pattern = "yyyy-MM-dd")
//    @FutureOrPresent(message = "Дата должна быть не позднее текущей даты")
//    private Date startDate;
//    @Transient
//    @Temporal(TemporalType.DATE)
//    @DateTimeFormat(pattern = "yyyy-MM-dd")
//    @FutureOrPresent(message = "Дата должна быть не позднее текущей даты")
//    private Date endDate;

    public Orders(Date dateOrder) {
        this.dateOrder = dateOrder;
    }
}
