package ru.andderv.order.TeacherOrdersApp.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.andderv.order.TeacherOrdersApp.models.SecurityPerson;
import ru.andderv.order.TeacherOrdersApp.services.SecurityPersonService;

/**
 * @author andderV
 * @date 29.10.2023 10:50
 * TeacherOrdersApp
 */
@Component
public class SecurityPersonValidator implements Validator {
    private final SecurityPersonService personService;

    @Autowired
    public SecurityPersonValidator(SecurityPersonService personService) {
        this.personService = personService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return SecurityPerson.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        SecurityPerson sPerson = (SecurityPerson) target;
        try {
            personService.loadUserByUsername(sPerson.getUserName());
        } catch (UsernameNotFoundException e){
            return;
        }
            errors.rejectValue("userName", "", "Человек с таким именем пользователя уже существует");
    }
}
