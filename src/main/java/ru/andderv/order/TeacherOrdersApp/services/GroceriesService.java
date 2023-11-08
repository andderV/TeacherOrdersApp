package ru.andderv.order.TeacherOrdersApp.services;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.andderv.order.TeacherOrdersApp.models.Groceries;
import ru.andderv.order.TeacherOrdersApp.models.MeasureUnit;
import ru.andderv.order.TeacherOrdersApp.models.Orders;
import ru.andderv.order.TeacherOrdersApp.repositories.GroceriesRepository;
import ru.andderv.order.TeacherOrdersApp.repositories.MeasureUnitRepository;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author andderV
 * @date 29.10.2023 10:03
 * TeacherOrdersApp
 */
@Service
@Transactional(readOnly = true)
public class GroceriesService {
    private final GroceriesRepository groceriesRepository;

    @Autowired
    public GroceriesService(GroceriesRepository groceriesRepository) {
        this.groceriesRepository = groceriesRepository;
    }

    public List<Groceries> findAll() {
        return groceriesRepository.findAll();
    }

    public Groceries findById(int id) {
        return groceriesRepository.findById(id).orElse(null);
    }

    @Transactional
    public void save(Groceries newProduct) {
        groceriesRepository.save(newProduct);
    }

    @Transactional
    public void update(int id, Groceries updateProduct) {
        updateProduct.setId(id);
        groceriesRepository.save(updateProduct);
    }

    @Transactional
    public void delete(int id) {
        groceriesRepository.deleteById(id);
    }

    public Groceries findProductByProductName(String productName){
        Optional<Groceries> product = groceriesRepository.findProductByProductName(productName);
        return product.orElse(null);
    }

    public List<Orders> getOrdersByProductId(int id) {
        Optional<Groceries> optionalProduct = groceriesRepository.findById(id);
        if (optionalProduct.isPresent()) {
            Hibernate.initialize(optionalProduct.get().getOrdersGroceriesList());
            return optionalProduct.get().getOrdersGroceriesList();
        } else {
            return Collections.emptyList();
        }
    }

//    public String getMeasureUnitByProductId(int id){
//        Optional<Groceries>found = groceriesRepository.findById(id);
//        if(found.isPresent()){
//            Optional<MeasureUnit>foundUnit = Optional.ofNullable(found.get().getMeasureUnit());
//            if(foundUnit.isPresent()){
//                return foundUnit.get().getMeasureUnitName();
//            }
//        }
//        return "единица измерения не определена";
//    }

}
