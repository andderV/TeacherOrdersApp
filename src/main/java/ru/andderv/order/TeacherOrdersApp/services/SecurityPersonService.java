package ru.andderv.order.TeacherOrdersApp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.andderv.order.TeacherOrdersApp.models.SecurityPerson;
import ru.andderv.order.TeacherOrdersApp.repositories.SecurityPersonRepository;
import ru.andderv.order.TeacherOrdersApp.security.SecurityPersonDetails;

import java.util.Optional;

/**
 * @author andderV
 * @date 11.11.2023 16:03
 * TeacherOrdersApp
 */
@Service
@Transactional(readOnly = true)
public class SecurityPersonService implements UserDetailsService {
    private final SecurityPersonRepository repository;

    @Autowired
    public SecurityPersonService(SecurityPersonRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<SecurityPerson> person = repository.findByUserName(username);

        if(person.isEmpty()){
            throw new UsernameNotFoundException("User not found!");
        }
        return new SecurityPersonDetails(person.get());
    }
}
