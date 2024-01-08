package ru.andderv.order.TeacherOrdersApp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.andderv.order.TeacherOrdersApp.models.Groceries;
import ru.andderv.order.TeacherOrdersApp.models.GroceryItem;
import ru.andderv.order.TeacherOrdersApp.models.Orders;
import ru.andderv.order.TeacherOrdersApp.models.Result;
import ru.andderv.order.TeacherOrdersApp.repositories.GroceryItemRepo;
import ru.andderv.order.TeacherOrdersApp.repositories.OrderRepository;

import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @author andderV
 * @date 18.11.2023 14:53
 * TeacherOrdersApp
 */
@Service
@Transactional(readOnly = true)
public class GroceryItemService {
    public final GroceryItemRepo groceryItemRepo;

    @Autowired
    public GroceryItemService(GroceryItemRepo groceryItemRepo) {
        this.groceryItemRepo = groceryItemRepo;
    }

    public List<GroceryItem>findAll(){
        return groceryItemRepo.findAll();
    }

    public List<GroceryItem>findAllWithSorting(boolean sortByProductName){
        if (sortByProductName) {
            return groceryItemRepo.findAll(Sort.by("grocery.productName").ascending());
        } else {
            return groceryItemRepo.findAll();
        }

    }

    public List<Result>sumTotalGroceryItemByGrocery(){
        return groceryItemRepo.sumTotalGroceryItemByGrocery();
    }

    public List<Result> sumTotalGroceryItemByGroceryWithDateBetween(Date start, Date end) {
        return groceryItemRepo.sumTotalGroceryItemByGroceryWithDateBetween(start, end);
    }

    public GroceryItem findById(int id){
        return groceryItemRepo.findById(id).orElse(null);
    }

    public List<GroceryItem> groceryItemList(Orders orders){
        return groceryItemRepo.findByOrder(orders);
    }

    @Transactional
    public void save(GroceryItem item) {
        groceryItemRepo.save(item);
    }

    @Transactional
    public void delete(int id) {
        groceryItemRepo.deleteById(id);
    }

}
