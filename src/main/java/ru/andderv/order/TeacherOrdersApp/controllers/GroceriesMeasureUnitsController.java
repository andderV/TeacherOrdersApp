package ru.andderv.order.TeacherOrdersApp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.andderv.order.TeacherOrdersApp.exception.AddingAnItemFromAnotherGroupException;
import ru.andderv.order.TeacherOrdersApp.models.Groceries;
import ru.andderv.order.TeacherOrdersApp.models.GroceriesMeasureUnit;
import ru.andderv.order.TeacherOrdersApp.models.MeasureUnit;
import ru.andderv.order.TeacherOrdersApp.services.GroceriesMeasureUnitService;
import ru.andderv.order.TeacherOrdersApp.services.GroceriesService;
import ru.andderv.order.TeacherOrdersApp.services.MeasureUnitService;

/**
 * @author andderV
 * @version 30.10.2024 7:15
 * TeacherOrdersApp
 */
@Controller
@RequestMapping("/groceriesMU")
public class GroceriesMeasureUnitsController {
    private final GroceriesService groceriesService;
    private final MeasureUnitService measureUnitService;
    private final GroceriesMeasureUnitService groceriesMeasureUnitService;

    public GroceriesMeasureUnitsController(GroceriesService groceriesService, MeasureUnitService measureUnitService, GroceriesMeasureUnitService groceriesMeasureUnitService) {
        this.groceriesService = groceriesService;
        this.measureUnitService = measureUnitService;
        this.groceriesMeasureUnitService = groceriesMeasureUnitService;
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int productId, Model model) {
        model.addAttribute("product", groceriesService.findById(productId));
        Groceries groceries = groceriesService.findById(productId);

        model.addAttribute("units", groceriesMeasureUnitService.groceryMeasureList(groceries));
        return "groceriesMU/show";
    }

    @GetMapping("/new/{id}")
    public String update(@PathVariable("id") int id,
                         Model model,
                         @ModelAttribute("unit") MeasureUnit unit,
                         @ModelAttribute("gmu") GroceriesMeasureUnit gmu) {
        model.addAttribute("product", groceriesService.findById(id));
        model.addAttribute("units", measureUnitService.findAll());
        return "groceriesMU/new";
    }

    @PatchMapping()
    public String addItem(
            @ModelAttribute("product") Groceries groceriesFromForm,
            @ModelAttribute("unit") MeasureUnit unitId,
            @ModelAttribute("gmu") GroceriesMeasureUnit gmu, Model model) {
        MeasureUnit unit = measureUnitService.findById(unitId.getMeasureUnitId());
        Groceries groceries = groceriesService.findProductByProductName(groceriesFromForm.getProductName());
        try {
            groceriesMeasureUnitService.save(groceries, unit, gmu);
        } catch (AddingAnItemFromAnotherGroupException e) {
            String message = e.getMessage();
            model.addAttribute("exception", message);
            model.addAttribute("units", measureUnitService.findAll());
            return "groceriesMU/new";
        }


        return "redirect:groceriesMU/" + groceries.getId();
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        int groceriesId = groceriesMeasureUnitService.findById(id).getGroceries().getId();
        groceriesMeasureUnitService.delete(id);
        return "redirect:" + groceriesId;
    }


}
