package ru.andderv.order.TeacherOrdersApp.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.andderv.order.TeacherOrdersApp.models.Providers;
import ru.andderv.order.TeacherOrdersApp.services.ContractsService;
import ru.andderv.order.TeacherOrdersApp.services.OrdersToSuppliersService;
import ru.andderv.order.TeacherOrdersApp.services.ProvidersService;
import ru.andderv.order.TeacherOrdersApp.util.ProviderValidator;

/**
 * @author andderV
 * @version 10.10.2024 7:07
 * TeacherOrdersApp
 */
@Controller
@RequestMapping("/providers")
public class ProviderController {
    private final ProvidersService providersService;
    private final ContractsService contractsService;
    private final OrdersToSuppliersService ordersToSuppliersService;
    private final ProviderValidator providerValidator;

    @Autowired
    public ProviderController(ProvidersService providersService, ContractsService contractsService, OrdersToSuppliersService ordersToSupplierService, ProviderValidator providerValidator) {
        this.providersService = providersService;
        this.contractsService = contractsService;
        this.ordersToSuppliersService = ordersToSupplierService;
        this.providerValidator = providerValidator;
    }

    @GetMapping
    public String index(@RequestParam(value = "sort_by_provider_name", required = false, defaultValue = "true") boolean sortByProviderName,
                        Model model) {
        model.addAttribute("providers", providersService.findAllWithSorting(sortByProviderName));
        return "providers/index";
    }

    @GetMapping("/{id}")
    public String show(Model model, @PathVariable("id") int id) {
        model.addAttribute("provider", providersService.findById(id));
        model.addAttribute("contracts", providersService.findContractsByProviderId(id));
        model.addAttribute("ordersToSupplier", ordersToSuppliersService.findAll());
        return "providers/show";
    }

    @GetMapping("/new")
    public String newProvider(@ModelAttribute("provider") Providers provider) {
        return "providers/new";
    }

    @PostMapping
    public String create(@ModelAttribute("provider") @Valid Providers provider,
                         BindingResult bindingResult) {

        providerValidator.validate(provider, bindingResult);

        if (bindingResult.hasErrors()) {
            return "providers/new";
        }
        providersService.save(provider);
        return "redirect:/providers";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("provider", providersService.findById(id));
        return "providers/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("provider") @Valid Providers provider,
                         BindingResult bindingResult,
                         @PathVariable("id") int id) {

        if (bindingResult.hasErrors()) {
            return "providers/edit";
        }
        providersService.update(id, provider);
        return "redirect:/providers";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        providersService.delete(id);
        return "redirect:/providers";
    }
}
