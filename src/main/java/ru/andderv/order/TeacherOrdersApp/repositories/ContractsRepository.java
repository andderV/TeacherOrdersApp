package ru.andderv.order.TeacherOrdersApp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.andderv.order.TeacherOrdersApp.models.Contracts;

import java.util.Optional;

/**
 * @author andderV
 * @version 24.09.2024 7:36
 * TeacherOrdersApp
 */
@Repository
public interface ContractsRepository extends JpaRepository<Contracts, Integer> {
    Optional<Contracts> findContractsByContractName(String name);
}
