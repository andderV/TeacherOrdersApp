package ru.andderv.order.TeacherOrdersApp.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.andderv.order.TeacherOrdersApp.models.Groceries;
import ru.andderv.order.TeacherOrdersApp.models.MeasureUnit;
import ru.andderv.order.TeacherOrdersApp.services.GroceriesService;
import ru.andderv.order.TeacherOrdersApp.services.MeasureUnitService;

/**
 * @author andderV
 * @date 29.10.2023 10:50
 * TeacherOrdersApp
 */
@Component
public class UnitValidator implements Validator {
    private final MeasureUnitService measureUnitService;

    @Autowired
    public UnitValidator(MeasureUnitService measureUnitService) {
        this.measureUnitService = measureUnitService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return MeasureUnit.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        MeasureUnit unit = (MeasureUnit) target;
        if (measureUnitService.findMeasureUnitByMeasureUnitName(unit.getMeasureUnitName()) != null){
            errors.rejectValue("measureUnitName", "", "Это название единицы измерения уже используется");
        }
    }
}
