package ru.andderv.order.TeacherOrdersApp.controllers;

import jakarta.validation.Valid;
import org.springframework.boot.actuate.web.exchanges.HttpExchange;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import ru.andderv.order.TeacherOrdersApp.models.Contracts;
import ru.andderv.order.TeacherOrdersApp.models.ContractsGroceries;
import ru.andderv.order.TeacherOrdersApp.models.Groceries;
import ru.andderv.order.TeacherOrdersApp.models.MeasureUnit;
import ru.andderv.order.TeacherOrdersApp.services.ContractsGroceriesService;
import ru.andderv.order.TeacherOrdersApp.services.ContractsService;
import ru.andderv.order.TeacherOrdersApp.services.GroceriesService;
import ru.andderv.order.TeacherOrdersApp.services.MeasureUnitService;

/**
 * @author andderV
 * @version 16.10.2024 7:35
 * TeacherOrdersApp
 */
@Controller
@RequestMapping("/contractGroceries")
public class ContractsGroceriesController {
    private final ContractsGroceriesService contractsGroceriesService;
    private final MeasureUnitService measureUnitService;
    private final GroceriesService groceriesService;
    private final ContractsService contractsService;

    public ContractsGroceriesController(ContractsGroceriesService contractsGroceriesService, MeasureUnitService measureUnitService, GroceriesService groceriesService, ContractsService contractsService) {
        this.contractsGroceriesService = contractsGroceriesService;
        this.measureUnitService = measureUnitService;
        this.groceriesService = groceriesService;
        this.contractsService = contractsService;
    }

    @GetMapping("/{id}")
    public String showItem(@PathVariable("id") int id, Model model) {
        int contractId = contractsGroceriesService.findById(id).getContract().getContractId();
        model.addAttribute("item", contractsGroceriesService.findById(id));
        model.addAttribute("fullListItem", contractsGroceriesService
                .groceryItemList(contractsService.findById(contractId)));
        return "cGroceries/show";
    }

    @GetMapping("{id}/new")
    public String newItem(@PathVariable("id") Integer contractId,
                          @ModelAttribute("cGroceries") ContractsGroceries cGroceries,
                          @ModelAttribute("product") Groceries product,
                          @ModelAttribute("unit") MeasureUnit unit,
                          @ModelAttribute("contract") Contracts contract,
                          Model model) {
        model.addAttribute("contract", contractsService.findById(contractId));
        model.addAttribute("units", measureUnitService.findAll());
        model.addAttribute("groceries", groceriesService.findAll(true));
        model.addAttribute("fullListItem", contractsGroceriesService
                .groceryItemList(contractsService.findById(contractId)));
        return "cGroceries/new";
    }

    @PostMapping
    public String create(@ModelAttribute("cGroceries") @Valid ContractsGroceries cGroceries,
                         BindingResult bindingResult,
                         @ModelAttribute("product") Groceries grocery,
                         @ModelAttribute("unit") MeasureUnit unit,
                         @ModelAttribute("contract") Contracts contract,
                         Model model) {
        model.addAttribute("fullListItem", contractsGroceriesService
                .groceryItemList(contractsService.findById(contract.getContractId())));


        if (bindingResult.hasErrors()) {
            return "cGroceries/new";

        }


        cGroceries.setMeasureUnit(measureUnitService.findById(unit.getMeasureUnitId()));
        cGroceries.setContract(contractsService.findById(contract.getContractId()));
        cGroceries.setProduct(groceriesService.findById(grocery.getId()));
        contractsGroceriesService.save(cGroceries);
        return "redirect:/contractGroceries/" + cGroceries.getId() + "/edit";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") Integer cGroceriesId,
                       @ModelAttribute("cGroceries") ContractsGroceries cGroceries,
                       @ModelAttribute("product") Groceries grocery,
                       @ModelAttribute("unit") MeasureUnit unit,
                       @ModelAttribute("contract") Contracts contract,
                       Model model) {
        model.addAttribute("cGroceries", contractsGroceriesService.findById(cGroceriesId));
        int contractId = contractsGroceriesService.findById(cGroceriesId).getContract().getContractId();
        System.out.println("contractId " + contractId);
        System.out.println("contract from cGroceries " + contractsGroceriesService.findById(cGroceriesId)
                .getContract().getContractName());
        model.addAttribute("contract", contractsService.findById(contractId));
        model.addAttribute("units", measureUnitService.findAll());
        model.addAttribute("groceries", groceriesService.findAll(true));
        model.addAttribute("fullListItem", contractsGroceriesService
                .groceryItemList(contractsService.findById(contractId)));
        return "cGroceries/edit";
    }

    //
    @PatchMapping
    public String addItem(@ModelAttribute("cGroceries") @Valid ContractsGroceries cGroceries,
                          BindingResult bindingResult,
                          @ModelAttribute("product") Groceries grocery,
                          @ModelAttribute("contract") Contracts contract,
                          @ModelAttribute("unit") MeasureUnit unit,
                          Model model) {

        int contractId = contract.getContractId();
        model.addAttribute("contract", contractsService.findById(contractId));
        model.addAttribute("fullListItem", contractsGroceriesService.groceryItemList(contractsService.findById(contractId)));

        if (bindingResult.hasErrors()) {
            for (ObjectError allError : bindingResult.getAllErrors()) {
                System.out.println(allError);
            }

            return "cGroceries/new";

        }


        cGroceries.setMeasureUnit(measureUnitService.findById(unit.getMeasureUnitId()));
        cGroceries.setProduct(groceriesService.findById(grocery.getId()));
        cGroceries.setContract(contractsService.findById(contract.getContractId()));
        contractsGroceriesService.save(cGroceries);
        return "redirect:/contractGroceries/" + cGroceries.getId() + "/edit";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Integer cGroceriesId) {
        int contractId = contractsGroceriesService.findById(cGroceriesId).getContract().getContractId();
        int id = contractsGroceriesService.findById(cGroceriesId).getId();

        contractsGroceriesService.delete(id);
        return "redirect:/contracts/" + contractId;
    }

}
