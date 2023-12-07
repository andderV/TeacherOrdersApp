package ru.andderv.order.TeacherOrdersApp.services;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.andderv.order.TeacherOrdersApp.models.Groceries;
import ru.andderv.order.TeacherOrdersApp.models.GroceryItem;
import ru.andderv.order.TeacherOrdersApp.models.Orders;
import ru.andderv.order.TeacherOrdersApp.repositories.GroceriesRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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

    public List<Groceries> findAll(boolean sortByProductName) {
        if (sortByProductName) {
            return groceriesRepository.findAll(Sort.by("productName"));
        } else {
            return groceriesRepository.findAll();
        }
    }

    public List<Groceries> findAllWithPaginationAndSorting(int pageNumber, int pageSize, boolean sortByProductName) {
        if (sortByProductName) {
            return groceriesRepository.findAll(PageRequest.of(pageNumber, pageSize, Sort.by("productName"))).getContent();
        } else {
            return groceriesRepository.findAll(PageRequest.of(pageNumber, pageSize)).getContent();
        }

    }

    public Page<Groceries> findAll(int pageNumber, int pageSize, boolean sortByProductName){
        if (sortByProductName){
            return groceriesRepository.findAll(PageRequest.of(pageNumber, pageSize, Sort.by("productName")));
        } else {
            return groceriesRepository.findAll(PageRequest.of(pageNumber, pageSize));
        }
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

    public Groceries findProductByProductName(String productName) {
        Optional<Groceries> product = groceriesRepository.findProductByProductName(productName);
        return product.orElse(null);
    }

    public List<GroceryItem> getOrdersByProductId(int id) {
        Optional<Groceries> optionalProduct = groceriesRepository.findById(id);
        if (optionalProduct.isPresent()) {
            Hibernate.initialize(optionalProduct.get().getGroceriesItem());
            return optionalProduct.get().getGroceriesItem();
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
