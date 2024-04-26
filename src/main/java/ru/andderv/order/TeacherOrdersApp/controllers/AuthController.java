package ru.andderv.order.TeacherOrdersApp.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.andderv.order.TeacherOrdersApp.exception.IncorrectKeyException;
import ru.andderv.order.TeacherOrdersApp.exception.IncorrectPasswordException;
import ru.andderv.order.TeacherOrdersApp.models.Teacher;
import ru.andderv.order.TeacherOrdersApp.services.RegistrationService;
import ru.andderv.order.TeacherOrdersApp.services.TeachersService;
import ru.andderv.order.TeacherOrdersApp.util.TeacherUserNameValidator;

/**
 * @author andderV
 * @date 16.01.2024 7:39
 * TeacherOrdersApp
 */
@Controller
@RequestMapping("/auth")
public class AuthController {

    private final TeacherUserNameValidator personValidator;

    private final RegistrationService registrationService;



    @Autowired
    public AuthController(TeacherUserNameValidator personValidator, RegistrationService registrationService) {
        this.personValidator = personValidator;
        this.registrationService = registrationService;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }

    @GetMapping("/registration")
    public String registrationPage(@ModelAttribute("person") Teacher teacher) {
        return "auth/registration";
    }

    @PostMapping("/registration")
    public String performRegistration(@ModelAttribute("person") @Valid Teacher teacher,
                                      BindingResult bindingResult,
                                      Model model) {
        personValidator.validate(teacher, bindingResult);

        if (bindingResult.hasErrors()) {
            return "auth/registration";
        }

        String keyFromForm = teacher.getKey();
        String expKey = "";
        try {
            registrationService.register(teacher, keyFromForm);

        } catch (IncorrectKeyException e) {
            expKey = e.getMessage();
            model.addAttribute("exceptionKey", expKey);
            return "auth/registration";
        } catch (IncorrectPasswordException e) {
            String expPass = e.getMessage();
            model.addAttribute("exceptionPass", expPass);
            return "auth/registration";
        }


        return "redirect:/auth/login";
    }

//    @GetMapping("/{id}/edit")
//    public String edit(Model model, @PathVariable("id") int id) {
//        model.addAttribute("teacher", teachersService.findById(id));
//        return "auth/edit";
//    }
//
//    @PatchMapping("/{id}")
//    public String update(@ModelAttribute("teacher") @Valid Teacher teacher,
//                         BindingResult bindingResult,
//                         @PathVariable("id") int id,
//                         Model model) {
//
//        if (bindingResult.hasErrors()) {
//            return "auth/edit";
//        }
////        String expPass;
//        try {
//            registrationService.update(id, teacher);
//        } catch (IncorrectPasswordException ex) {
//            expPass = ex.getMessage();
//            model.addAttribute("exceptionPass", expPass);
//        }
//        return "redirect:/adminPage";
//    }
//
//    @DeleteMapping("/{id}")
//    public String delete(@PathVariable("id") int id) {
//        teachersService.delete(id);
//        return "redirect:/adminPage";
//    }
}
