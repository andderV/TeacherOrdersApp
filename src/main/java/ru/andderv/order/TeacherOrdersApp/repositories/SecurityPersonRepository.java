package ru.andderv.order.TeacherOrdersApp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.andderv.order.TeacherOrdersApp.models.SecurityPerson;

import java.util.Optional;

/**
 * @author andderV
 * @date 11.11.2023 16:01
 * TeacherOrdersApp
 */
@Repository
public interface SecurityPersonRepository extends JpaRepository<SecurityPerson, Integer> {
    Optional<SecurityPerson>findByUserName(String userName);
}
