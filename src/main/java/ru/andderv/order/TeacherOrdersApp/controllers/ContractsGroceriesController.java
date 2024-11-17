package ru.andderv.order.TeacherOrdersApp.controllers;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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

    @GetMapping("/new")
    public String newItem(@RequestParam(value = "contractId", required = false) Integer contractId,
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
        model.addAttribute("contract", contractsService.findById(contract.getContractId()));
        model.addAttribute("groceries", groceriesService.findAll(true));
        model.addAttribute("units", measureUnitService.findAll());
        model.addAttribute("fullListItem", contractsGroceriesService
                .groceryItemList(contractsService.findById(contract.getContractId())));

        if (bindingResult.hasErrors()) {
            return "cGroceries/new";

        }

        cGroceries.setContract(contract);
        cGroceries.setProduct(grocery);
//        cGroceries.setMeasureUnitId(unit);
        contractsGroceriesService.save(cGroceries);
        return "redirect:/cGroceries/new?contractId=" + contract.getContractId();
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") Integer contractId,
                       @ModelAttribute("cGroceries") ContractsGroceries cGroceries,
                       @ModelAttribute("product") Groceries grocery,
                       @ModelAttribute("unit") MeasureUnit unit,
                       @ModelAttribute("contract") Contracts contract,
                       Model model) {
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
                          @ModelAttribute("unit") MeasureUnit unit) {

        if (bindingResult.hasErrors()) {
            return "cGroceries/new";

        }

        cGroceries.setProduct(grocery);
        cGroceries.setContract(contract);
//        cGroceries.setMeasureUnit(unit);
        contractsGroceriesService.save(cGroceries);
        return "redirect:/cGroceries/" + contract.getContractId() + "/edit";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        int contractId = contractsGroceriesService.findById(id).getContract().getContractId();
        contractsGroceriesService.delete(id);
        return "redirect:/contracts/" + contractId;
    }

}
