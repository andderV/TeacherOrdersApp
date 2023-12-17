package ru.andderv.order.TeacherOrdersApp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.andderv.order.TeacherOrdersApp.models.Groceries;
import ru.andderv.order.TeacherOrdersApp.models.GroceryItem;
import ru.andderv.order.TeacherOrdersApp.models.Orders;
import ru.andderv.order.TeacherOrdersApp.repositories.GroceryItemRepo;
import ru.andderv.order.TeacherOrdersApp.repositories.OrderRepository;

import java.util.Collections;
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
    public final OrderRepository orderRepository;

    @Autowired
    public GroceryItemService(GroceryItemRepo groceryItemRepo, OrderRepository orderRepository) {
        this.groceryItemRepo = groceryItemRepo;
        this.orderRepository = orderRepository;
    }

//    public List<GroceryItem>findAll(){
//        return groceryItemRepo.findAll();
//    }

//    public List<GroceryItem>findAllWithSorting(boolean sortByDateOrder){
//        if (sortByDateOrder) {
//            return groceryItemRepo.findAll(Sort.by("order.dateOrder").descending());
//        } else {
//            return groceryItemRepo.findAll();
//        }
//
//    }

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
