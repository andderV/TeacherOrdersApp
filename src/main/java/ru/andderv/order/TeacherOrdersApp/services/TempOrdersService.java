package ru.andderv.order.TeacherOrdersApp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.andderv.order.TeacherOrdersApp.models.TempOrders;
import ru.andderv.order.TeacherOrdersApp.repositories.TempOrdersRepository;

import java.util.List;

/**
 * @author andderV
 * @version 25.09.2024 7:26
 * TeacherOrdersApp
 */
@Service
@Transactional(readOnly = true)
public class TempOrdersService {
    private final TempOrdersRepository repository;
    @Autowired
    public TempOrdersService(TempOrdersRepository repository) {
        this.repository = repository;
    }

    public List<TempOrders> findAll() {
     return repository.findAll();
    }

    public TempOrders findById(int id) {
        return repository.findById(id).orElse(null);
    }

    @Transactional
    public void save(TempOrders tempOrders) {
        repository.save(tempOrders);
    }

    @Transactional
    public void update(int id, TempOrders tempOrdersUpdate) {
        tempOrdersUpdate.setId(id);
        repository.save(tempOrdersUpdate);
    }

    @Transactional
    public void delete(TempOrders tempOrders) {
        repository.delete(tempOrders);
    }
}
