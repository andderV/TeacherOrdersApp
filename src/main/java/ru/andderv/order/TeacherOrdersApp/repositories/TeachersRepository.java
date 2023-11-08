package ru.andderv.order.TeacherOrdersApp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.andderv.order.TeacherOrdersApp.models.Teacher;

import java.util.Optional;

/**
 * @author andderV
 * @date 29.10.2023 9:56
 * TeacherOrdersApp
 */
@Repository
public interface TeachersRepository extends JpaRepository<Teacher, Integer> {
    Optional<Teacher> findTeacherByTeacherName(String name);
    Optional<Teacher> findTeacherByEmail(String email);
    Optional<Teacher> findTeacherByPhoneNumber(String phoneNumber);
}
