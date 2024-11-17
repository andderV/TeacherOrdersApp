package ru.andderv.order.TeacherOrdersApp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.andderv.order.TeacherOrdersApp.models.MeasureUnit;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * @author andderV
 * @date 29.10.2023 10:02
 * TeacherOrdersApp
 */
@Repository
public interface MeasureUnitRepository extends JpaRepository<MeasureUnit, Integer>{
    Optional<MeasureUnit> findMeasureUnitByMeasureUnitName(String measureUnitName);
    Optional<Set<MeasureUnit>> findMeasureUnitsByGroceriesId(int groceriesId);
    Optional<MeasureUnit> findMeasureUnitByGroupIdAndRatioIs(int groupId, float ratio);
}
