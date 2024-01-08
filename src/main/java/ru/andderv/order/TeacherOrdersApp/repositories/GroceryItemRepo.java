package ru.andderv.order.TeacherOrdersApp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.andderv.order.TeacherOrdersApp.models.GroceryItem;
import ru.andderv.order.TeacherOrdersApp.models.Orders;
import ru.andderv.order.TeacherOrdersApp.models.Result;

import java.util.Date;
import java.util.List;

/**
 * @author andderV
 * {@code @date} 18.11.2023 14:42
 * TeacherOrdersApp
 */
@Repository
public interface GroceryItemRepo extends JpaRepository<GroceryItem, Integer> {
    List<GroceryItem> findByOrder(Orders orders);

    @Query("SELECT new ru.andderv.order.TeacherOrdersApp.models.Result(gi.grocery.productName, " +
           "sum(gi.quantity), gi.grocery.measureUnit.measureUnitName, gi.grocery.id) " +
           "from GroceryItem as gi group by gi.grocery.productName order by gi.grocery.productName asc")
    List<Result> sumTotalGroceryItemByGrocery();

    @Query("SELECT new ru.andderv.order.TeacherOrdersApp.models.Result(gi.grocery.productName, " +
           "sum(gi.quantity), gi.grocery.measureUnit.measureUnitName, gi.grocery.id) " +
           "from GroceryItem as gi where gi.order.dateOrder between :start AND :end " +
           "group by gi.grocery.productName order by gi.grocery.productName asc")
    List<Result> sumTotalGroceryItemByGroceryWithDateBetween(@Param("start") Date start, @Param("end") Date end);


}
