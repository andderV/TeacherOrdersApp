package ru.andderv.order.TeacherOrdersApp.services;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.andderv.order.TeacherOrdersApp.models.Orders;
import ru.andderv.order.TeacherOrdersApp.models.Teacher;
import ru.andderv.order.TeacherOrdersApp.repositories.TeachersRepository;
import ru.andderv.order.TeacherOrdersApp.security.TeacherDetails;

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
public class TeachersService implements UserDetailsService {
    private final TeachersRepository teachersRepository;

    @Autowired
    public TeachersService(TeachersRepository teachersRepository) {
        this.teachersRepository = teachersRepository;
    }

    public List<Teacher> findAll() {
        return teachersRepository.findAll();
    }

    public List<Teacher> findAllWithSorting(boolean sortByTeacherName){
        if (sortByTeacherName){
            return teachersRepository.findAll(Sort.by("teacherName"));
        } else {
           return teachersRepository.findAll();
        }
    }

    public Teacher findById(int id) {
        return teachersRepository.findById(id).orElse(null);
    }

    @Transactional
    public void save(Teacher newTeacher) {
        teachersRepository.save(newTeacher);
    }

    @Transactional
    public void update(int id, Teacher updateTeacher) {
        updateTeacher.setTeacherId(id);
        teachersRepository.save(updateTeacher);
    }

    @Transactional
    public void delete(int id) {
        teachersRepository.deleteById(id);
    }

    public Teacher findTeacherByTeacherName(String teacherName){
        Optional<Teacher> teacher = teachersRepository.findTeacherByTeacherName(teacherName);
        return teacher.orElse(null);
    }

    public Teacher findTeacherByUserName(String userName){
        Optional<Teacher> teacher = teachersRepository.findTeacherByUserName(userName);
        return teacher.orElse(null);
    }

    public Teacher findTeacherByEmail(String email){
        return teachersRepository.findTeacherByEmail(email).orElse(null);
    }

    public Teacher findByPhoneNumber(String phoneNumber){
        return teachersRepository.findTeacherByTeacherName(phoneNumber).orElse(null);
    }

    public List<Orders> getOrdersByTeacherId(int id) {
        Optional<Teacher> optionalTeacher = teachersRepository.findById(id);
        if (optionalTeacher.isPresent()) {
            Hibernate.initialize(optionalTeacher.get().getOrdersList());
            return optionalTeacher.get().getOrdersList();
        } else {
            return Collections.emptyList();
        }
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Teacher> person = teachersRepository.findByUserName(username);

        if(person.isEmpty()){
            throw new UsernameNotFoundException("User not found!");
        }
        return new TeacherDetails(person.get());
    }
}
