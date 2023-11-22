package ru.andderv.order.TeacherOrdersApp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.andderv.order.TeacherOrdersApp.models.Groceries;
import ru.andderv.order.TeacherOrdersApp.models.GroceryItem;
import ru.andderv.order.TeacherOrdersApp.models.Orders;
import ru.andderv.order.TeacherOrdersApp.services.GroceriesService;

import java.util.List;
import java.util.Optional;

/**
 * @author andderV
 * {@code @date} 18.11.2023 14:42
 * TeacherOrdersApp
 */
@Repository
public interface GroceryItemRepo extends JpaRepository<GroceryItem, Integer> {
    List<GroceryItem> findByOrder(Orders orders);
}
