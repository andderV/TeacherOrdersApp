package ru.andderv.order.TeacherOrdersApp.controllers;

import jakarta.validation.Valid;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.andderv.order.TeacherOrdersApp.models.GroupOfMU;
import ru.andderv.order.TeacherOrdersApp.services.GroupOfMUService;
import ru.andderv.order.TeacherOrdersApp.services.MeasureUnitService;
import ru.andderv.order.TeacherOrdersApp.util.GroupValidator;

/**
 * @author andderV
 * @version 28.10.2024 9:15
 * TeacherOrdersApp
 */
@Controller
@RequestMapping("/groups")
public class GroupController {
    private final GroupOfMUService groupService;
    public final MeasureUnitService measureUnit;
    private final GroupValidator groupValidator;

    public GroupController(GroupOfMUService group, MeasureUnitService measureUnit, GroupValidator groupValidator) {
        this.groupService = group;
        this.measureUnit = measureUnit;
        this.groupValidator = groupValidator;
    }

    @GetMapping
    public String index(@RequestParam(value = "sort_by_group_name", required = false, defaultValue = "true") boolean sortByGroupName, Model model, Sort sort) {
        model.addAttribute("groups", groupService.findAllWithSorting(sortByGroupName));
        return "groups/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("group", groupService.findById(id));
        model.addAttribute("units", groupService.findAllMeasureUnitsByGroupId(id));
        return "groups/show";
    }

    @GetMapping("/new")
    public String newUnit(@ModelAttribute("group") GroupOfMU group) {
        return "groups/new";
    }

    @PostMapping
    public String create(@ModelAttribute("group") @Valid GroupOfMU group,
                         BindingResult bindingResult) {

        groupValidator.validate(group, bindingResult);

        if (bindingResult.hasErrors()) {
            return "groups/new";
        }
        groupService.save(group);
        return "redirect:/groups";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("group", groupService.findById(id));
        return "groups/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("group") @Valid GroupOfMU group,
                         BindingResult bindingResult,
                         @PathVariable("id") int id) {

        if (bindingResult.hasErrors()) {
            return "groups/edit";
        }

        groupService.update(id, group);
        return "redirect:/groups";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        groupService.delete(id);
        return "redirect:/groups";
    }


}
