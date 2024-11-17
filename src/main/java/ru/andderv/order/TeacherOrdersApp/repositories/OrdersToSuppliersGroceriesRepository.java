package ru.andderv.order.TeacherOrdersApp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.andderv.order.TeacherOrdersApp.models.OrdersToSuppliersGrocery;

/**
 * @author andderV
 * @version 25.09.2024 7:36
 * TeacherOrdersApp
 */
@Repository
public interface OrdersToSuppliersGroceriesRepository extends JpaRepository<OrdersToSuppliersGrocery, Integer> {
}
