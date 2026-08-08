package ru.andderv.order.TeacherOrdersApp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.andderv.order.TeacherOrdersApp.models.OrdersToSupplier;
import ru.andderv.order.TeacherOrdersApp.models.OrdersToSuppliersGrocery;
import ru.andderv.order.TeacherOrdersApp.repositories.OrdersToSuppliersGroceriesRepository;

import java.util.List;

/**
 * @author andderV
 * @version 26.09.2024 6:14
 * TeacherOrdersApp
 */
@Service
@Transactional(readOnly = true)
public class OrdersToSuppliersGroceriesService {
    private final OrdersToSuppliersGroceriesRepository ordersToSuppliersGroceriesRepository;

    @Autowired
    public OrdersToSuppliersGroceriesService(OrdersToSuppliersGroceriesRepository ordersToSuppliersGroceriesRepository) {
        this.ordersToSuppliersGroceriesRepository = ordersToSuppliersGroceriesRepository;
    }

    public List<OrdersToSuppliersGrocery> findAll() {
        return ordersToSuppliersGroceriesRepository.findAll();
    }

    public OrdersToSuppliersGrocery findById(int id) {
        return ordersToSuppliersGroceriesRepository.findById(id).orElse(null);
    }

    public List<OrdersToSuppliersGrocery> groceryList(OrdersToSupplier orders) {
        return ordersToSuppliersGroceriesRepository.findByOrder(orders);
    }

    @Transactional
    public void save(OrdersToSuppliersGrocery ordersToSuppliersGrocery) {
        ordersToSuppliersGroceriesRepository.save(ordersToSuppliersGrocery);
    }

    @Transactional
    public void update(int id, OrdersToSuppliersGrocery ordersToSuppliersGroceryUpdate) {
        ordersToSuppliersGroceryUpdate.setId(id);
        ordersToSuppliersGroceriesRepository.save(ordersToSuppliersGroceryUpdate);
    }

    @Transactional
    public void delete(int id) {
        ordersToSuppliersGroceriesRepository.deleteById(id);
    }

}