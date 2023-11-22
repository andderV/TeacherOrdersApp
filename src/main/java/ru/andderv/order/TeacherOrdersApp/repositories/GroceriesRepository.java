package ru.andderv.order.TeacherOrdersApp.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.andderv.order.TeacherOrdersApp.models.Groceries;
import ru.andderv.order.TeacherOrdersApp.models.MeasureUnit;

import java.util.Optional;

/**
 * @author andderV
 * @date 29.10.2023 9:59
 * TeacherOrdersApp
 */
@Repository
public interface GroceriesRepository extends JpaRepository<Groceries, Integer> {

    Optional<Groceries> findProductByProductName(String productName);

    Page<Groceries>findByProductNameContainingIgnoreCase(String keyword, Pageable pageable);

}
