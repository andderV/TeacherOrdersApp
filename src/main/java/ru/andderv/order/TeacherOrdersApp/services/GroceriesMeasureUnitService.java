package ru.andderv.order.TeacherOrdersApp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.andderv.order.TeacherOrdersApp.exception.AddingAnItemFromAnotherGroupException;
import ru.andderv.order.TeacherOrdersApp.models.*;
import ru.andderv.order.TeacherOrdersApp.repositories.GroceriesMeasureUnitRepo;

import java.util.List;
import java.util.Optional;

/**
 * @author andderV
 * @version 13.11.2024 12:55
 * TeacherOrdersApp
 */
@Service
@Transactional(readOnly = true)
public class GroceriesMeasureUnitService {
    private final GroceriesMeasureUnitRepo repo;;

    @Autowired
    public GroceriesMeasureUnitService(GroceriesMeasureUnitRepo repo) {
        this.repo = repo;
    }

    public List<GroceriesMeasureUnit> findAll() {
        return repo.findAll();
    }

    public GroceriesMeasureUnit findById(int id) {
        return repo.findById(id).orElse(null);
    }

    public List<GroceriesMeasureUnit> groceryMeasureList(Groceries groceries) {
        return repo.findByGroceries(groceries);
    }

    public List<GroceriesMeasureUnit> unitGroceriesList(MeasureUnit measureUnit) {
        return repo.findByMeasureUnit(measureUnit);
    }

    @Transactional
    public void save(Groceries groceries, MeasureUnit unit, GroceriesMeasureUnit gmu) throws AddingAnItemFromAnotherGroupException{
        Optional<GroupOfMU> groupExpected = groceries.getMeasureUnits().stream().findAny().map(MeasureUnit::getGroup);
        GroupOfMU groupActual = unit.getGroup();
        if (groupExpected.isPresent()) {
            if (groupExpected.get().equals(groupActual)) {
                gmu.setMeasureUnit(unit);
                gmu.setGroceries(groceries);
                repo.save(gmu);
            } else if (!groupExpected.get().equals(groupActual)){
                throw new AddingAnItemFromAnotherGroupException("Нельзя добавить единицу измерения из другой группы");
            }
        }
    }

    @Transactional
    public void update(int id, GroceriesMeasureUnit gmu){
        gmu.setId(id);
        repo.save(gmu);
    }

    @Transactional
    public void delete(int id){
        repo.deleteById(id);
    }

}
