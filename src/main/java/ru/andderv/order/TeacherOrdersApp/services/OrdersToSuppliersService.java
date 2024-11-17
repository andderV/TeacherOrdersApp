package ru.andderv.order.TeacherOrdersApp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.andderv.order.TeacherOrdersApp.models.OrdersToSupplier;
import ru.andderv.order.TeacherOrdersApp.repositories.OrdersToSuppliersRepository;

import java.util.List;

/**
 * @author andderV
 * @version 26.09.2024 6:10
 * TeacherOrdersApp
 */
@Service
@Transactional(readOnly = true)
public class OrdersToSuppliersService {
    private final OrdersToSuppliersRepository ordersToSuppliersRepository;

    @Autowired
    public OrdersToSuppliersService(OrdersToSuppliersRepository ordersToSuppliersRepository) {
        this.ordersToSuppliersRepository = ordersToSuppliersRepository;
    }

    public List<OrdersToSupplier> findAll() {
        return ordersToSuppliersRepository.findAll();
    }

    public OrdersToSupplier findById(int id) {
        return ordersToSuppliersRepository.findById(id).orElse(null);
    }

    @Transactional
    public void save(OrdersToSupplier ordersToSupplier) {
        ordersToSuppliersRepository.save(ordersToSupplier);
    }

    @Transactional
    public void update(int id, OrdersToSupplier ordersToSupplierUpdate) {
        ordersToSupplierUpdate.setId(id);
        ordersToSuppliersRepository.save(ordersToSupplierUpdate);
    }

    @Transactional
    public void delete(int id) {
        ordersToSuppliersRepository.deleteById(id);
    }
}
