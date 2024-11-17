package ru.andderv.order.TeacherOrdersApp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.andderv.order.TeacherOrdersApp.models.Contracts;
import ru.andderv.order.TeacherOrdersApp.models.Providers;
import ru.andderv.order.TeacherOrdersApp.repositories.ContractsRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author andderV
 * @version 24.09.2024 19:15
 * TeacherOrdersApp
 */
@Service
@Transactional(readOnly = true)
public class ContractsService {
    private final ContractsRepository repo;

    @Autowired
    public ContractsService(ContractsRepository repo) {
        this.repo = repo;
    }

    public Contracts findContractByContractName(String contractName) {
        Optional<Contracts> contracts = repo.findContractsByContractName(contractName);
        return contracts.orElse(null);
    }

    public List<Contracts> findAll() {
        return repo.findAll();
    }

    public List<Contracts> findAllWithSorting(boolean ascending) {
        if (ascending) {
            return repo.findAll(Sort.by("contractName"));
        } else {
            return repo.findAll();
        }
    }

    public Contracts findById(int id) {
        return repo.findById(id).orElse(null);
    }

    @Transactional
    public void save(Contracts contract) {
        repo.save(contract);
    }

    @Transactional
    public void update(int id, Contracts contractUpdate) {
        contractUpdate.setContractId(id);
        repo.save(contractUpdate);
    }

    @Transactional
    public void delete(int id){
        repo.deleteById(id);
    }
}
