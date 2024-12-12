package ru.andderv.order.TeacherOrdersApp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.andderv.order.TeacherOrdersApp.models.*;

import java.util.List;

/**
 * @author andderV
 * @version 25.09.2024 7:35
 * TeacherOrdersApp
 */
@Repository
public interface ContractsGroceriesRepository extends JpaRepository<ContractsGroceries, Integer> {
    List<ContractsGroceries> findByContract(Contracts contract);
    List<ContractsGroceries> findAllByProductAndMeasureUnit(Groceries groceries, MeasureUnit measureUnit);
    List<ContractsGroceries> findByProduct(Groceries groceries);
    List<ContractsGroceries> findByMeasureUnit(MeasureUnit measureUnit);

}
