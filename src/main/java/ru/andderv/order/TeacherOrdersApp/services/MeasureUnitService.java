package ru.andderv.order.TeacherOrdersApp.services;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.andderv.order.TeacherOrdersApp.models.ContractsGroceries;
import ru.andderv.order.TeacherOrdersApp.models.Groceries;
import ru.andderv.order.TeacherOrdersApp.models.MeasureUnit;
import ru.andderv.order.TeacherOrdersApp.models.Orders;
import ru.andderv.order.TeacherOrdersApp.repositories.GroceriesRepository;
import ru.andderv.order.TeacherOrdersApp.repositories.MeasureUnitRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * @author andderV
 * @date 29.10.2023 10:03
 * TeacherOrdersApp
 */
@Service
@Transactional(readOnly = true)
public class MeasureUnitService {
    private final MeasureUnitRepository measureUnitRepository;

    @Autowired
    public MeasureUnitService(MeasureUnitRepository measureUnitRepository) {
        this.measureUnitRepository = measureUnitRepository;
    }

    public List<MeasureUnit> findAll() {
        return measureUnitRepository.findAll();
    }

    public MeasureUnit findById(int id) {
        return measureUnitRepository.findById(id).orElse(null);
    }

    @Transactional
    public void save(MeasureUnit newUnit) {
        measureUnitRepository.save(newUnit);
    }

    @Transactional
    public void update(int id, MeasureUnit updateUnit) {
        updateUnit.setMeasureUnitId(id);
        measureUnitRepository.save(updateUnit);
    }

    @Transactional
    public void delete(int id) {
        measureUnitRepository.deleteById(id);
    }

    public MeasureUnit findMeasureUnitByMeasureUnitName(String measureUnitName){
        Optional<MeasureUnit> product = measureUnitRepository.findMeasureUnitByMeasureUnitName(measureUnitName);
        return product.orElse(null);
    }

    public List<MeasureUnit> findAllWithSorting(boolean sortByGroupName) {
        if (sortByGroupName){
            return measureUnitRepository.findAll(Sort.by("group.groupName"));
        } else {
            return measureUnitRepository.findAll();
        }
    }

    public Set<MeasureUnit> findMeasureUnitsByGroceriesId(Integer groceriesId) {
        Optional<Set<MeasureUnit>> set = measureUnitRepository.findMeasureUnitsByGroceriesId(groceriesId);
        return set.orElse(null);
    }

    public MeasureUnit findMeasureUnitByGroupIdAndRatioIs(int groupId, float ratio) {
        Optional<MeasureUnit> unit = measureUnitRepository.findMeasureUnitByGroupIdAndRatioIs(groupId, ratio);
        return unit.orElse(null);
    }

}
