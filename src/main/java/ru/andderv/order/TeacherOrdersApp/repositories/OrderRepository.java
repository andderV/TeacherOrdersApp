package ru.andderv.order.TeacherOrdersApp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.andderv.order.TeacherOrdersApp.models.Orders;

/**
 * @author andderV
 * @date 29.10.2023 9:58
 * TeacherOrdersApp
 */
@Repository
public interface OrderRepository extends JpaRepository<Orders,Integer> {
}
