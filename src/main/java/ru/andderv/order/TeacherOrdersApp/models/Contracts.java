package ru.andderv.order.TeacherOrdersApp.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/**
 * @author andderV
 * @version 26.08.2024 6:57
 * TeacherOrdersApp
 */
@Entity
@Table(name = "contracts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Contracts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contract_id")
    private int contractId;

    @Column(name = "contract_name", nullable = false, unique = true)
    @NotEmpty(message = "Поле не может быть пустым")
    @Size(min = 6, max = 10, message = "Название контракта должно быть не менее 6 символов и не более 10 символов")
    private String contractName;

    @Column(name = "date_start")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
//    @NotEmpty(message = "Поле не может быть пустым")
    private Date dateStart;

    @Column(name = "date_end")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
//    @NotEmpty(message = "Поле не может быть пустым")
    private Date dateEnd;

    @Column(name = "sum", nullable = false)
    @NotNull(message = "Сумма не должна быть 0")
    @Positive(message = "Сумма не должна быть меньше 0")
    private BigDecimal sum;

    @ManyToOne
    @JoinColumn(name = "provider_id", referencedColumnName = "provider_id")
    private Providers provider;

    @OneToMany(mappedBy = "contract", cascade = {CascadeType.ALL})
    private List<ContractsGroceries> contractsGroceries;

    @OneToMany(mappedBy = "contract", cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private List<OrdersToSupplier> ordersToSuppliers;
}
