package ru.andderv.order.TeacherOrdersApp.util;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.andderv.order.TeacherOrdersApp.models.Providers;
import ru.andderv.order.TeacherOrdersApp.services.ProvidersService;

/**
 * @author andderV
 * @version 25.08.2024 18:49
 * TeacherOrdersApp
 */
@Component
public class ProviderValidator implements Validator {
    private final ProvidersService providersService;

    @Autowired
    public ProviderValidator(ProvidersService providersService) {
        this.providersService = providersService;
    }


    @Override
    public boolean supports(Class<?> clazz) {
        return Providers.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Providers providers = (Providers) target;
        if (providersService.findProviderByProviderName(providers.getProviderName()) != null){
            errors.rejectValue("providerName", "", "Это название поставщика уже используется");
        }
    }
}
