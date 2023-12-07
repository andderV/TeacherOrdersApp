package ru.andderv.order.TeacherOrdersApp.util;

import org.springframework.beans.factory.annotation.Autowired;
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
public class TeacherValidator implements Validator {
    private final TeachersService teachersService;

    @Autowired
    public TeacherValidator(TeachersService teachersService) {
        this.teachersService = teachersService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Teacher.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Teacher teacher = (Teacher) target;
        if (teachersService.findTeacherByTeacherName(teacher.getTeacherName()) != null){
            errors.rejectValue("teacherName", "", "Это имя преподавателя уже используется");
        }
    }
}
