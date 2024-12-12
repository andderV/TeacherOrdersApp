package ru.andderv.order.TeacherOrdersApp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.andderv.order.TeacherOrdersApp.models.*;
import ru.andderv.order.TeacherOrdersApp.repositories.ContractsGroceriesRepository;

import java.util.List;

/**
 * @author andderV
 * @version 25.09.2024 7:38
 * TeacherOrdersApp
 */
@Service
@Transactional(readOnly = true)
public class ContractsGroceriesService {
    private final ContractsGroceriesRepository repository;

    @Autowired
    public ContractsGroceriesService(ContractsGroceriesRepository repository) {
        this.repository = repository;
    }
    public List<ContractsGroceries> findAll() {
        return repository.findAll();
    }

    public ContractsGroceries findById(int id) {
        return repository.findById(id).orElse(null);
    }

    public List<ContractsGroceries> groceryItemList(Contracts contracts){
        return repository.findByContract(contracts);
    }

    public List<ContractsGroceries> groceriesList(Groceries groceries){
        return repository.findByProduct(groceries);
    }

    public List<ContractsGroceries> unitList(MeasureUnit measureUnit){
        return repository.findByMeasureUnit(measureUnit);
    }

    public List<ContractsGroceries> groceryUnitList(Groceries groceries, MeasureUnit measureUnit){
        return repository.findAllByProductAndMeasureUnit(groceries, measureUnit);
    }


    @Transactional
    public void save(ContractsGroceries contractsGroceries) {
        repository.save(contractsGroceries);
    }

    @Transactional
    public void delete(int id) {
        repository.deleteById(id);
    }

    @Transactional
    public void update(int id, ContractsGroceries contractsGroceriesUpdate) {
        contractsGroceriesUpdate.setId(id);
        repository.save(contractsGroceriesUpdate);
    }
}
