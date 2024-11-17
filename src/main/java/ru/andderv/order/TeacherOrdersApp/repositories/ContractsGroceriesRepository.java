package ru.andderv.order.TeacherOrdersApp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.andderv.order.TeacherOrdersApp.models.Contracts;
import ru.andderv.order.TeacherOrdersApp.models.ContractsGroceries;
import ru.andderv.order.TeacherOrdersApp.models.GroceryItem;
import ru.andderv.order.TeacherOrdersApp.models.Orders;

import java.util.List;

/**
 * @author andderV
 * @version 25.09.2024 7:35
 * TeacherOrdersApp
 */
@Repository
public interface ContractsGroceriesRepository extends JpaRepository<ContractsGroceries, Integer> {
    List<ContractsGroceries> findByContract(Contracts contract);

}
