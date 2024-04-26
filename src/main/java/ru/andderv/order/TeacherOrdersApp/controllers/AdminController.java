package ru.andderv.order.TeacherOrdersApp.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.andderv.order.TeacherOrdersApp.exception.IncorrectPasswordException;
import ru.andderv.order.TeacherOrdersApp.models.Teacher;
import ru.andderv.order.TeacherOrdersApp.services.RegistrationService;
import ru.andderv.order.TeacherOrdersApp.services.TeachersService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author andderV
 * @date 23.04.2024 18:47
 * TeacherOrdersApp
 */
@Controller
@RequestMapping("/admin")
public class AdminController {
    private final TeachersService teachersService;
    private final RegistrationService registrationService;

    @Autowired
    public AdminController(TeachersService teachersService, RegistrationService registrationService) {
        this.teachersService = teachersService;
        this.registrationService = registrationService;
    }

    @GetMapping
    public String index(@RequestParam(value = "sort_by_teacher_name", required = false, defaultValue = "true") boolean sortByTeacherName,
                        Model model) {
        model.addAttribute("teacher", teachersService.findAllWithSorting(sortByTeacherName));
        return "admin/adminPage";
    }
    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("teacher", teachersService.findById(id));
        Set<String> roles = teachersService.findAll().stream().map(Teacher::getRole).collect(Collectors.toSet());
        roles.forEach(System.out::println);
        model.addAttribute("roles", roles);
        return "admin/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("teacher") @Valid Teacher teacher,
                         BindingResult bindingResult,
                         @PathVariable("id") int id,
                         Model model) {

        if (bindingResult.hasErrors()) {
            return "admin/edit";
        }
//        String expPass;
        try {
            registrationService.update(id, teacher);
        } catch (IncorrectPasswordException ex) {
            String expPass = ex.getMessage();
            model.addAttribute("exceptionPass", expPass);
        }
        return "redirect:/admin";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        teachersService.delete(id);
        return "redirect:/admin";
    }
}
