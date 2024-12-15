package ru.andderv.order.TeacherOrdersApp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.andderv.order.TeacherOrdersApp.models.Contracts;
import ru.andderv.order.TeacherOrdersApp.models.ContractsGroceries;
import ru.andderv.order.TeacherOrdersApp.models.Groceries;
import ru.andderv.order.TeacherOrdersApp.models.MeasureUnit;

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
    @Query("SELECT SUM(c.sumBudget) FROM ContractsGroceries c WHERE c.contract = :contract GROUP BY c.contract")
    Integer sumBudgetTotal(Contracts contract);

    @Query("SELECT SUM(c.sumOffBudget) FROM ContractsGroceries c WHERE c.contract = :contract GROUP BY c.contract")
    Integer sumOffBudgetTotal(Contracts contract);

}
