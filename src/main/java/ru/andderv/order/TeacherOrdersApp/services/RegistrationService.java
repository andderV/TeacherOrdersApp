package ru.andderv.order.TeacherOrdersApp.services;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.andderv.order.TeacherOrdersApp.exception.IncorrectKeyException;
import ru.andderv.order.TeacherOrdersApp.exception.IncorrectPasswordException;
import ru.andderv.order.TeacherOrdersApp.models.Teacher;
import ru.andderv.order.TeacherOrdersApp.repositories.TeachersRepository;

/**
 * @author andderV
 * @date 23.01.2024 7:16
 * TeacherOrdersApp
 */
@Service
public class RegistrationService {

    private final PasswordEncoder passwordEncoder;

    private final TeachersRepository personRepository;

    @Autowired
    public RegistrationService(PasswordEncoder passwordEncoder, TeachersRepository personRepository) {
        this.passwordEncoder = passwordEncoder;
        this.personRepository = personRepository;
    }

    @Transactional
    public void register(Teacher person, String keyFromForm) throws IncorrectKeyException, IncorrectPasswordException {
        person.setUserPassword(passwordEncoder.encode(person.getUserPassword()));
        person.setRole("ROLE_USER");
        if (isConfirmPassword(person) && isCheckingKey(person, keyFromForm)) {
            personRepository.save(person);
            } else if (!isCheckingKey(person, keyFromForm)){
                throw new IncorrectKeyException("Неверный ключ");
            } else if (!isConfirmPassword(person)){
            throw new IncorrectPasswordException("Пароли не совпадают");
        }

    }

    private boolean isCheckingKey(Teacher person, String keyFromForm) {
        String fullKey = person.getTeacherName() + " " + "ПК33";
        String encoded = DigestUtils.sha256Hex(fullKey);

        return encoded.equals(keyFromForm);
    }

    private boolean isConfirmPassword(Teacher person) {
        return passwordEncoder.matches(person.getConfirmUserPassword(), person.getUserPassword());
    }

}
