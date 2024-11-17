package ru.andderv.order.TeacherOrdersApp.util;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.andderv.order.TeacherOrdersApp.models.GroupOfMU;
import ru.andderv.order.TeacherOrdersApp.models.Providers;
import ru.andderv.order.TeacherOrdersApp.services.GroupOfMUService;
import ru.andderv.order.TeacherOrdersApp.services.ProvidersService;

/**
 * @author andderV
 * @version 25.08.2024 18:49
 * TeacherOrdersApp
 */
@Component
public class GroupValidator implements Validator {
    private final GroupOfMUService groupOfMUService;

    @Autowired
    public GroupValidator(GroupOfMUService groupOfMUService) {
        this.groupOfMUService = groupOfMUService;
    }


    @Override
    public boolean supports(Class<?> clazz) {
        return GroupOfMU.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        GroupOfMU group = (GroupOfMU) target;
        if (groupOfMUService.findGroupOfMuByGroupName(group.getGroupName()) != null){
            errors.rejectValue("groupName", "", "Это название группы уже используется");
        }
    }
}
