package ru.andderv.order.TeacherOrdersApp.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.andderv.order.TeacherOrdersApp.models.*;
import ru.andderv.order.TeacherOrdersApp.services.*;

/**
 * @author andderV
 * @date 10.11.2023 19:14
 * TeacherOrdersApp
 */
@Controller
@RequestMapping("/ordersSuppliers")
public class OrderToSuppliersController {
    private final OrdersToSuppliersService service;
    private final OrdersToSuppliersGroceriesService groceriesService;
    private final ProvidersService providerService;
    private final ContractsService contractsService;
    private final MeasureUnitService measureUnitService;


    @Autowired
    public OrderToSuppliersController(OrdersToSuppliersService service, OrdersToSuppliersGroceriesService groceriesService,
                                      ProvidersService providerService, ContractsService contractsService,
                                      MeasureUnitService measureUnitService) {
        this.service = service;
        this.groceriesService = groceriesService;
        this.providerService = providerService;
        this.contractsService = contractsService;
        this.measureUnitService = measureUnitService;
    }

    @GetMapping
    public String index(@RequestParam(value = "sort", required = false, defaultValue = "true") Boolean sort,
                        Model model) {
        model.addAttribute("orders", service.findAllWithSorting(sort));
        return "orderToSupplier/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        OrdersToSupplier order = service.findById(id);
        model.addAttribute("order", order);
        model.addAttribute("groceryItem", groceriesService.groceryList(service.findById(id)));
        return "orderToSupplier/show";
    }

    @GetMapping("/new")
    public String newOrder(@RequestParam(value = "id", required = false) Integer id,
                           @ModelAttribute("order") OrdersToSupplier order,
                           @ModelAttribute("item") OrdersToSuppliersGrocery item,
                           @ModelAttribute("provider") Providers provider,
                           @ModelAttribute("contract") Contracts contract,
                           @ModelAttribute("productEmpty") Groceries product,
                           @ModelAttribute("unit") MeasureUnit unit,
                           Model model) {

        model.addAttribute("providers", providerService.findAll());
        model.addAttribute("contracts", contractsService.findAll());
        model.addAttribute("products", groceriesService.findAll());
        model.addAttribute("units", measureUnitService.findAll());
        model.addAttribute("groceryItem", groceriesService.groceryList(service.findById(id)));


        return "orderToSupplier/new";
    }

    @PostMapping
    public String create(@ModelAttribute("order") @Valid OrdersToSupplier order,
                         BindingResult bindingResult,
                         @ModelAttribute("provider") Providers provider,
                         @ModelAttribute("contract") Contracts contract,
                         @ModelAttribute("product") Groceries product,
                         @ModelAttribute("unit") MeasureUnit unit,
                         Model model) {

        model.addAttribute("groceryItem", groceriesService.groceryList(service.findById(order.getId())));


        if (bindingResult.hasErrors()) {
            return "orderToSupplier/new";
        }
        order.setProvider(provider);
        service.save(order);
        int id = order.getId();
        return "redirect:/orderToSupplier/new?orderId=" + id;
    }

//    @GetMapping("/{id}/edit")
//    public String edit(Model model, @PathVariable("id") int id) {
//        Orders order = orderService.findById(id);
//        model.addAttribute("order", order);
//        model.addAttribute("teachers", teachersService.findAll());
//        model.addAttribute("owner", orderService.findById(id).getOwner());
//        model.addAttribute("groceryItem", groceryItemService.groceryItemList(order));
//
//        return "order/edit";
//    }
//
//    @PatchMapping("/{id}")
//    public String update(@ModelAttribute("order") @Valid Orders order,
//                         BindingResult bindingResult,
//                         @ModelAttribute("owner") Teacher owner,
//                         @PathVariable("id") int id,
//                         Model model) {
//        model.addAttribute("teachers", teachersService.findAll());
//
//
//        if (bindingResult.hasErrors()) {
//            return "order/edit";
//        }
//
//        order.setOwner(owner);
//        orderService.update(id, order);
//        return "redirect:/orders";
//    }
//
//    @DeleteMapping("/{id}")
//    public String delete(@PathVariable("id") int id) {
//        orderService.delete(id);
//        return "redirect:/orders";
//    }


}
