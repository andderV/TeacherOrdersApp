package ru.andderv.order.TeacherOrdersApp.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.andderv.order.TeacherOrdersApp.models.GroupOfMU;
import ru.andderv.order.TeacherOrdersApp.models.MeasureUnit;
import ru.andderv.order.TeacherOrdersApp.services.GroupOfMUService;
import ru.andderv.order.TeacherOrdersApp.services.MeasureUnitService;
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
    private final GroupOfMUService groupOfMUService;

    @Autowired
    public UnitController(MeasureUnitService unitService, UnitValidator unitValidator, GroupOfMUService groupOfMUService) {
        this.unitService = unitService;
        this.unitValidator = unitValidator;
        this.groupOfMUService = groupOfMUService;
    }

    @GetMapping
    public String index(@RequestParam(value = "sort_by_group_name", required = false, defaultValue = "true") boolean sortByGroupName,
                        Model model) {
        model.addAttribute("measureUnit", unitService.findAll());
        model.addAttribute("measureUnit", unitService.findAllWithSorting(sortByGroupName));
        return "unit/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("unit", unitService.findById(id));
        model.addAttribute("groceries", unitService.findById(id).getGroceries());
        return "unit/show";
    }

    @GetMapping("/new")
    public String newUnit(@ModelAttribute("unit") MeasureUnit unit,
                          @ModelAttribute("group") GroupOfMU groupOfMU,
                          Model model) {
        model.addAttribute("groups", groupOfMUService.findAll());
        return "unit/new";
    }

    @PostMapping
    public String create(@ModelAttribute("unit") @Valid MeasureUnit unit,
                         BindingResult bindingResult,
                         @ModelAttribute("group") GroupOfMU groupOfMU,
                         Model model) {

        unitValidator.validate(unit, bindingResult);

        if (bindingResult.hasErrors()) {
            model.addAttribute("groups", groupOfMUService.findAll());
            return "unit/new";
        }
        unit.setGroup(groupOfMU);
        unitService.save(unit);
        return "redirect:/units";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("unit", unitService.findById(id));
        model.addAttribute("groups", groupOfMUService.findAll());
        model.addAttribute("group", unitService.findById(id).getGroup());
        return "unit/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("unit") @Valid MeasureUnit unit,
                         BindingResult bindingResult,
                         @ModelAttribute("group") GroupOfMU groupOfMU,
                         @PathVariable("id") int id) {

        if (bindingResult.hasErrors()) {
            return "unit/edit";
        }

        unitService.update(id, unit);
        return "redirect:/units";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        unitService.delete(id);
        return "redirect:/units";
    }


}
