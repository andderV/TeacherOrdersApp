package ru.andderv.order.TeacherOrdersApp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.andderv.order.TeacherOrdersApp.models.SecurityPerson;
import ru.andderv.order.TeacherOrdersApp.repositories.SecurityPersonRepository;

/**
 * @author andderV
 * @date 23.01.2024 7:16
 * TeacherOrdersApp
 */
@Service
public class RegistrationService {

    private final PasswordEncoder passwordEncoder;

    private final SecurityPersonRepository personRepository;

    @Autowired
    public RegistrationService(PasswordEncoder passwordEncoder, SecurityPersonRepository personRepository) {
        this.passwordEncoder = passwordEncoder;
        this.personRepository = personRepository;
    }

    @Transactional
    public void register(SecurityPerson person){
        person.setUserPassword(passwordEncoder.encode(person.getUserPassword()));
        person.setRole("ROLE_USER");
        personRepository.save(person);
    }
}
