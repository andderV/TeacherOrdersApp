package ru.andderv.order.TeacherOrdersApp.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.andderv.order.TeacherOrdersApp.models.Teacher;
import ru.andderv.order.TeacherOrdersApp.services.TeachersService;
import ru.andderv.order.TeacherOrdersApp.util.TeacherValidator;

/**
 * @author andderV
 * @date 29.10.2023 19:12
 * TeacherOrdersApp
 */
@Controller
@RequestMapping("/teachers")
public class TeacherController {
    private final TeachersService teachersService;
    private final TeacherValidator teacherValidator;

    @Autowired
    public TeacherController(TeachersService teachersService, TeacherValidator teacherValidator) {
        this.teachersService = teachersService;
        this.teacherValidator = teacherValidator;
    }

    @GetMapping
    public String index(Model model){
        model.addAttribute("teachers", teachersService.findAll());
        return "teachers/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model){
        model.addAttribute("teacher", teachersService.findById(id));
        model.addAttribute("orders", teachersService.getOrdersByTeacherId(id));
        return "teachers/show";
    }

    @GetMapping("/new")
    public String newTeacher(@ModelAttribute("teacher") Teacher teacher){
        return "teachers/new";
    }

    @PostMapping
    public String create(@ModelAttribute("teacher") @Valid Teacher teacher,
                         BindingResult bindingResult){

        teacherValidator.validate(teacher, bindingResult);

        if (bindingResult.hasErrors()){
            return "teachers/new";
        }
        teachersService.save(teacher);
        return "redirect:/teachers";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id){
        model.addAttribute("teacher", teachersService.findById(id));
        return "teachers/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("teacher") @Valid Teacher teacher,
                         BindingResult bindingResult,
                         @PathVariable("id") int id){

        if (bindingResult.hasErrors()){
            return "teachers/edit";
        }

        teachersService.update(id, teacher);
        return "redirect:/teachers";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        teachersService.delete(id);
        return "redirect:/teachers";
    }


}
