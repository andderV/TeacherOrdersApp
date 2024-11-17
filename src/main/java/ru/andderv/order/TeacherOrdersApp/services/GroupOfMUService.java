package ru.andderv.order.TeacherOrdersApp.services;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.andderv.order.TeacherOrdersApp.models.GroupOfMU;
import ru.andderv.order.TeacherOrdersApp.models.MeasureUnit;
import ru.andderv.order.TeacherOrdersApp.models.Providers;
import ru.andderv.order.TeacherOrdersApp.repositories.GroupOfMURepo;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * @author andderV
 * @version 28.10.2024 9:06
 * TeacherOrdersApp
 */
@Service
@Transactional(readOnly = true)
public class GroupOfMUService {
    private final GroupOfMURepo groupOfMURepo;

    @Autowired
    public GroupOfMUService(GroupOfMURepo groupOfMURepo) {
        this.groupOfMURepo = groupOfMURepo;
    }

    public List<GroupOfMU> findAll() {
        return groupOfMURepo.findAll();
    }

    public GroupOfMU findById(int id) {
        return groupOfMURepo.findById(id).orElse(null);
    }

    public List<GroupOfMU> findAllWithSorting(boolean ascending) {
        if (ascending) {
            return groupOfMURepo.findAll(Sort.by("groupName"));
        } else {
            return groupOfMURepo.findAll();
        }
    }

    public List<MeasureUnit> findAllMeasureUnitsByGroupId(int groupId) {
        Optional<GroupOfMU> group = groupOfMURepo.findById(groupId);
        if (group.isPresent()) {
            Hibernate.initialize(group.get().getUnits());
            return group.get().getUnits();
        } else {
            return Collections.emptyList();
        }
    }

    @Transactional
    public void save(GroupOfMU newGroup) {
        groupOfMURepo.save(newGroup);
    }

    @Transactional
    public void update(int id, GroupOfMU updateGroup) {
        updateGroup.setId(id);
        groupOfMURepo.save(updateGroup);
    }

    @Transactional
    public void delete(int id) {
        groupOfMURepo.deleteById(id);
    }

    public GroupOfMU findGroupOfMuByGroupName(String groupName){
        Optional<GroupOfMU> group = groupOfMURepo.findGroupOfMUByGroupName(groupName);
        return group.orElse(null);
    }
}
