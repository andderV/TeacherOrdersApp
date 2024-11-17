package ru.andderv.order.TeacherOrdersApp.util;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.andderv.order.TeacherOrdersApp.models.Contracts;
import ru.andderv.order.TeacherOrdersApp.services.ContractsService;

/**
 * @author andderV
 * @version 25.08.2024 18:49
 * TeacherOrdersApp
 */
@Component
public class ContractValidator implements Validator {
    private final ContractsService contractsService;

    @Autowired
    public ContractValidator(ContractsService contractsService) {
        this.contractsService = contractsService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Contracts.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Contracts contract = (Contracts) target;
        if (contractsService.findContractByContractName(contract.getContractName()) != null){
            errors.rejectValue("contractName", "", "Это название контракта уже используется");
        }
    }
}
