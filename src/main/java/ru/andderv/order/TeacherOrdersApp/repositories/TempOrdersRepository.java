package ru.andderv.order.TeacherOrdersApp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.andderv.order.TeacherOrdersApp.models.TempOrders;

/**
 * @author andderV
 * @version 25.09.2024 7:25
 * TeacherOrdersApp
 */
@Repository
public interface TempOrdersRepository extends JpaRepository<TempOrders, Integer> {
}
