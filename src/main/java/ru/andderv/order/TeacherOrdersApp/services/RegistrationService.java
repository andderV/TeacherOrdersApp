package ru.andderv.order.TeacherOrdersApp.services;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.andderv.order.TeacherOrdersApp.exception.IncorrectKeyException;
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
    public void register(SecurityPerson person, String keyFromForm) throws IncorrectKeyException {
        person.setUserPassword(passwordEncoder.encode(person.getUserPassword()));
        person.setRole("ROLE_USER");
        if (isCheckingKey(person, keyFromForm)) {
            personRepository.save(person);
        } else {
            throw new IncorrectKeyException("Неверный ключ");
        }
    }

    private boolean isCheckingKey(SecurityPerson person, String keyFromForm) {
        String encoded = person.getUserName() + " " + "ПК33";
        String coded = DigestUtils.sha256Hex(encoded);

        return coded.equals(keyFromForm);
    }

}
