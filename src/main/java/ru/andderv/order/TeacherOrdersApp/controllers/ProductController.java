package ru.andderv.order.TeacherOrdersApp.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.andderv.order.TeacherOrdersApp.models.Groceries;
import ru.andderv.order.TeacherOrdersApp.models.MeasureUnit;
import ru.andderv.order.TeacherOrdersApp.services.GroceriesService;
import ru.andderv.order.TeacherOrdersApp.services.MeasureUnitService;
import ru.andderv.order.TeacherOrdersApp.util.ProductValidator;

/**
 * @author andderV
 * @date 29.10.2023 19:12
 * TeacherOrdersApp
 */
@Controller
@RequestMapping("/groceries")
public class ProductController {
    private final GroceriesService groceriesService;
    private final MeasureUnitService unitService;
    private final ProductValidator productValidator;

    @Autowired
    public ProductController(GroceriesService groceriesService, MeasureUnitService unitService, ProductValidator productValidator) {
        this.groceriesService = groceriesService;
        this.unitService = unitService;
        this.productValidator = productValidator;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("groceries", groceriesService.findAll());
        return "groceries/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("product", groceriesService.findById(id));
        model.addAttribute("orders", groceriesService.getOrdersByProductId(id));
//        model.addAttribute("unit", groceriesService.getMeasureUnitByProductId(id));

        return "groceries/show";
    }

    @GetMapping("/new")
    public String newProduct(@ModelAttribute("product") Groceries product, @ModelAttribute("unit") MeasureUnit measureUnit,
                             Model model) {
        model.addAttribute("units", unitService.findAll());
        return "groceries/new";
    }

    @PostMapping
    public String create(@ModelAttribute("product") @Valid Groceries product,
                         BindingResult bindingResult,
                         @ModelAttribute("unit") MeasureUnit unit) {

        productValidator.validate(product, bindingResult);

        if (bindingResult.hasErrors()) {
            return "groceries/new";
        }
        product.setMeasureUnit(unit);
        groceriesService.save(product);
        return "redirect:/groceries";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("product", groceriesService.findById(id));
        model.addAttribute("units", unitService.findAll());
        return "groceries/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("product") @Valid Groceries product,
                         BindingResult bindingResult,
                         @ModelAttribute("unit") MeasureUnit unit,
                         @PathVariable("id") int id) {

        if (bindingResult.hasErrors()) {
            return "groceries/edit";
        }


        groceriesService.update(id, product);
        return "redirect:/groceries";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        groceriesService.delete(id);
        return "redirect:/groceries";
    }


}
