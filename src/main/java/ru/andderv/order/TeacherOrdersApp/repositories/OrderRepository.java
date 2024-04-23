package ru.andderv.order.TeacherOrdersApp.repositories;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.andderv.order.TeacherOrdersApp.models.OrderResult;
import ru.andderv.order.TeacherOrdersApp.models.Orders;

import java.util.Date;
import java.util.List;

/**
 * @author andderV
 * @date 29.10.2023 9:58
 * TeacherOrdersApp
 */
@Repository
public interface OrderRepository extends JpaRepository<Orders,Integer> {

    @Query("SELECT new ru.andderv.order.TeacherOrdersApp.models.OrderResult" +
           "(o.orderId, o.dateOrder, o.owner.teacherName) from Orders as o " +
           "where o.dateOrder between :start AND :end " +
           "order by o.dateOrder, o.owner.teacherName")
    List<OrderResult> findAllByDateOrderBetweenStartAndEnd(@Param("start") Date start, @Param("end") Date end);

    List<Orders> findAllByOwnerTeacherId(int teacherId, Sort dateOrder);
    List<Orders> findAllByOwnerTeacherId(int teacherId);
}
