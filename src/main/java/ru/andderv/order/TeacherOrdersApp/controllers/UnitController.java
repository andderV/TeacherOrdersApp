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
import ru.andderv.order.TeacherOrdersApp.util.UnitValidator;

/**
 * @author andderV
 * @date 29.10.2023 19:12
 * TeacherOrdersApp
 */
@Controller
@RequestMapping("/units")
public class UnitController {
    private final MeasureUnitService unitService;
    private final UnitValidator unitValidator;

    @Autowired
    public UnitController(MeasureUnitService unitService, UnitValidator unitValidator) {
        this.unitService = unitService;
        this.unitValidator = unitValidator;
    }

    @GetMapping
    public String index(Model model){
        model.addAttribute("measureUnit", unitService.findAll());
        return "unit/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model){
        model.addAttribute("unit", unitService.findById(id));
        model.addAttribute("units", unitService.getProductByMeasureUnitId(id));
        return "unit/show";
    }

    @GetMapping("/new")
    public String newUnit(@ModelAttribute("unit")MeasureUnit unit){
        return "unit/new";
    }

    @PostMapping
    public String create(@ModelAttribute("unit") @Valid MeasureUnit unit,
                         BindingResult bindingResult){

        unitValidator.validate(unit, bindingResult);

        if (bindingResult.hasErrors()){
            return "unit/new";
        }
        unitService.save(unit);
        return "redirect:/units";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id){
        model.addAttribute("unit", unitService.findById(id));
        return "unit/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("product") @Valid MeasureUnit unit,
                         BindingResult bindingResult,
                         @PathVariable("id") int id){

        if (bindingResult.hasErrors()){
            return "unit/edit";
        }

        unitService.update(id, unit);
        return "redirect:/units";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        unitService.delete(id);
        return "redirect:/units";
    }


}
