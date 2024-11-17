package ru.andderv.order.TeacherOrdersApp.services;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.andderv.order.TeacherOrdersApp.models.Contracts;
import ru.andderv.order.TeacherOrdersApp.models.Providers;
import ru.andderv.order.TeacherOrdersApp.models.Teacher;
import ru.andderv.order.TeacherOrdersApp.repositories.ProvidersRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * @author andderV
 * @version 23.09.2024 18:58
 * TeacherOrdersApp
 **/
@Service
@Transactional(readOnly = true)
public class ProvidersService {
    private final ProvidersRepository providerRepository;

    @Autowired
    public ProvidersService(ProvidersRepository providerRepository) {
        this.providerRepository = providerRepository;
    }


    public Providers findProviderByProviderName(String providerName) {
        Optional<Providers> providers = providerRepository.findProvidersByProviderName(providerName);
        return providers.orElse(null);
    }

    public List<Contracts> findContractsByProviderId(int providerId) {
        Optional<Providers> provider = providerRepository.findById(providerId);
        if (provider.isPresent()) {
            Hibernate.initialize(provider.get().getContracts());
            return provider.get().getContracts();
        } else {
            return Collections.emptyList();
        }
    }

    public List<Providers> findAll() {
        return providerRepository.findAll();
    }

    public List<Providers> findAllWithSorting(boolean ascending) {
        if (ascending) {
            return providerRepository.findAll(Sort.by("providerName"));
        } else {
            return providerRepository.findAll();
        }
    }

    public Providers findById(int id) {
        return providerRepository.findById(id).orElse(null);
    }

    @Transactional
    public void save(Providers provider) {
        providerRepository.save(provider);
    }

    @Transactional
    public void delete(int id) {
        providerRepository.deleteById(id);
    }

    @Transactional
    public void update(int id, Providers providerUpdate) {
        providerUpdate.setProviderId(id);
        providerRepository.save(providerUpdate);
    }
}
