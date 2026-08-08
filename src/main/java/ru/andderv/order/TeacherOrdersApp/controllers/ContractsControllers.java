package ru.andderv.order.TeacherOrdersApp.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.andderv.order.TeacherOrdersApp.models.Contracts;
import ru.andderv.order.TeacherOrdersApp.models.ContractsGroceries;
import ru.andderv.order.TeacherOrdersApp.models.Providers;
import ru.andderv.order.TeacherOrdersApp.services.ContractsGroceriesService;
import ru.andderv.order.TeacherOrdersApp.services.ContractsService;
import ru.andderv.order.TeacherOrdersApp.services.ProvidersService;
import ru.andderv.order.TeacherOrdersApp.util.ContractValidator;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author andderV
 * @version 07.10.2024 6:35
 * TeacherOrdersApp
 */

//TODO Проверить работу метода show
@Controller
@RequestMapping("/contracts")
public class ContractsControllers {
    private final ContractsService contractsService;
    private final ProvidersService providersService;
    private final ContractValidator contractValidator;
    private final ContractsGroceriesService contractsGroceriesService;

    @Autowired
    public ContractsControllers(ContractsService contractsService, ProvidersService providersService, ContractValidator contractValidator, ContractsGroceriesService contractsGroceriesService) {
        this.contractsService = contractsService;
        this.providersService = providersService;
        this.contractValidator = contractValidator;
        this.contractsGroceriesService = contractsGroceriesService;
    }

    @GetMapping
    public String index(@RequestParam(value = "sort_by_contract_name", required = false, defaultValue = "true") boolean sortByContractName,
                        Model model) {
        model.addAttribute("contracts", contractsService.findAllWithSorting(sortByContractName));
        return "contracts/index";
    }

    @GetMapping("/{id}")
    public String show(Model model, @PathVariable("id") int id) {
        Contracts contract = contractsService.findById(id);
        List<ContractsGroceries> contractsGroceries = contract.getContractsGroceries();
        model.addAttribute("contract", contract);
        model.addAttribute("contractGroceries", contractsGroceries);
        BigDecimal sumBudgetTotal = contractsGroceriesService.sumBudgetTotal(contract);
        BigDecimal sumOffBudgetTotal = contractsGroceriesService.sumOffBudgetTotal(contract);
        if ((sumBudgetTotal != null) && (sumOffBudgetTotal != null)) {
            BigDecimal sumTotal = sumBudgetTotal.add(sumOffBudgetTotal);
            model.addAttribute("sumBudgetTotal", sumBudgetTotal);
            model.addAttribute("sumOffBudgetTotal", sumOffBudgetTotal);
            model.addAttribute("sumTotal", sumTotal);
        } else {
            model.addAttribute("sumBudgetTotal", null);
            model.addAttribute("sumOffBudgetTotal", null);
            model.addAttribute("sumTotal", null);
        }
        return "contracts/show";
    }

    @GetMapping("/new")
    public String newContract(@ModelAttribute("contract") Contracts contract,
                              @ModelAttribute("provider") Providers provider,
                              Model model) {
        model.addAttribute("providers", providersService.findAll());
        return "contracts/new";
    }

    @PostMapping
    public String create(@ModelAttribute("contract") @Valid Contracts contract,
                         BindingResult bindingResult,
                         @ModelAttribute("provider") Providers provider,
                         Model model) {

        contractValidator.validate(contract, bindingResult);

        if (bindingResult.hasErrors()) {
            model.addAttribute("providers", providersService.findAll());
            return "contracts/new";
        }
        contract.setProvider(provider);
        contractsService.save(contract);
        return "redirect:/contracts";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        Contracts contract = contractsService.findById(id);
        model.addAttribute("contract", contract);
        model.addAttribute("providers", providersService.findAll());
        model.addAttribute("owner", contractsService.findById(id).getProvider());
        return "contracts/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("contract") @Valid Contracts contract,
                         BindingResult bindingResult,
                         @ModelAttribute("provider") Providers provider,
                         @PathVariable("id") int id) {

        if (bindingResult.hasErrors()) {
            return "contracts/edit";
        }
        contract.setProvider(provider);
        contractsService.update(id, contract);
        return "redirect:/contracts";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        contractsService.delete(id);
        return "redirect:/contracts";
    }


}
