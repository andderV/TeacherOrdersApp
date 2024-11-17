package ru.andderv.order.TeacherOrdersApp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.andderv.order.TeacherOrdersApp.models.Providers;

import java.util.Optional;

/**
 * @author andderV
 * @version 23.09.2024 18:57
 * TeacherOrdersApp
 */
@Repository
public interface ProvidersRepository extends JpaRepository<Providers, Integer> {
    Optional<Providers> findProvidersByProviderName(String providerName);
}
