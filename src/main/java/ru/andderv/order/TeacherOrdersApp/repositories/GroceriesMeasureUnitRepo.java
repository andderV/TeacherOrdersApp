package ru.andderv.order.TeacherOrdersApp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.andderv.order.TeacherOrdersApp.models.Groceries;
import ru.andderv.order.TeacherOrdersApp.models.GroceriesMeasureUnit;
import ru.andderv.order.TeacherOrdersApp.models.MeasureUnit;

import java.util.List;

/**
 * @author andderV
 * @version 13.11.2024 12:53
 * TeacherOrdersApp
 */
@Repository
public interface GroceriesMeasureUnitRepo extends JpaRepository<GroceriesMeasureUnit, Integer> {
    List<GroceriesMeasureUnit> findByGroceries(Groceries groceries);
    List<GroceriesMeasureUnit> findByMeasureUnit(MeasureUnit measureUnit);
}
