package ru.andderv.order.TeacherOrdersApp.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.andderv.order.TeacherOrdersApp.models.Groceries;
import ru.andderv.order.TeacherOrdersApp.models.Teacher;
import ru.andderv.order.TeacherOrdersApp.services.GroceriesService;
import ru.andderv.order.TeacherOrdersApp.services.TeachersService;

/**
 * @author andderV
 * @date 29.10.2023 10:50
 * TeacherOrdersApp
 */
@Component
public class ProductValidator implements Validator {
    private final GroceriesService groceriesService;

    @Autowired
    public ProductValidator(GroceriesService groceriesService) {
        this.groceriesService = groceriesService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Groceries.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Groceries product = (Groceries) target;
        if (groceriesService.findProductByProductName(product.getProductName()) != null){
            errors.rejectValue("productName", "", "This productName is already taken");
        }
    }
}
