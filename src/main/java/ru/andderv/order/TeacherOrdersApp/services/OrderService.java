package ru.andderv.order.TeacherOrdersApp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.andderv.order.TeacherOrdersApp.models.Orders;
import ru.andderv.order.TeacherOrdersApp.repositories.OrderRepository;

import java.util.List;

/**
 * @author andderV
 * @date 10.11.2023 19:08
 * TeacherOrdersApp
 */
@Service
@Transactional(readOnly = true)
public class OrderService {
    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<Orders> findAll() {
        return orderRepository.findAll();
    }

    public Orders findById(int id) {
        return orderRepository.findById(id).orElse(null);
    }

    @Transactional
    public void save(Orders newOrder) {
        orderRepository.save(newOrder);
    }

    @Transactional
    public void update(int id, Orders updateOrders) {
        updateOrders.setOrderId(id);
        orderRepository.save(updateOrders);
    }

    @Transactional
    public void delete(int id) {
        orderRepository.deleteById(id);
    }

}
