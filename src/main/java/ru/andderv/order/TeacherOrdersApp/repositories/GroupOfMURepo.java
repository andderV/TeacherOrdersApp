package ru.andderv.order.TeacherOrdersApp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.andderv.order.TeacherOrdersApp.models.GroupOfMU;
import ru.andderv.order.TeacherOrdersApp.models.MeasureUnit;

import java.util.Optional;

/**
 * @author andderV
 * @version 28.10.2024 9:05
 * TeacherOrdersApp
 */
@Repository
public interface GroupOfMURepo extends JpaRepository<GroupOfMU, Integer> {
    Optional<GroupOfMU> findGroupOfMUByGroupName(String groupName);
}
