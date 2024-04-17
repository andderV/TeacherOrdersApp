package ru.andderv.order.TeacherOrdersApp.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.andderv.order.TeacherOrdersApp.models.Teacher;
import ru.andderv.order.TeacherOrdersApp.services.TeachersService;

/**
 * @author andderV
 * @date 29.10.2023 10:50
 * TeacherOrdersApp
 */
@Component
public class TeacherUserNameValidator implements Validator {
//    private final SecurityPersonService personService;
    private final TeachersService teachersService;

    @Autowired
    public TeacherUserNameValidator(TeachersService teachersService) {
        this.teachersService = teachersService;
//        this.personService = personService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Teacher.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Teacher sTeacher = (Teacher) target;
        try {
            teachersService.loadUserByUsername(sTeacher.getUserName());
        } catch (UsernameNotFoundException e){
            return;
        }
            errors.rejectValue("userName", "", "Пользователь с таким логином уже существует");
    }
}
