package ru.andderv.order.TeacherOrdersApp.controllers;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.andderv.order.TeacherOrdersApp.exception.IncorrectKeyException;
import ru.andderv.order.TeacherOrdersApp.models.SecurityPerson;
import ru.andderv.order.TeacherOrdersApp.services.RegistrationService;
import ru.andderv.order.TeacherOrdersApp.util.SecurityPersonValidator;

/**
 * @author andderV
 * @date 16.01.2024 7:39
 * TeacherOrdersApp
 */
@Controller
@RequestMapping("/auth")
public class AuthController {

    private final SecurityPersonValidator personValidator;

    private final RegistrationService registrationService;


    public AuthController(SecurityPersonValidator personValidator, RegistrationService registrationService) {
        this.personValidator = personValidator;
        this.registrationService = registrationService;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }

    @GetMapping("/registration")
    public String registrationPage(@ModelAttribute("person") SecurityPerson person) {
        return "auth/registration";
    }

    @PostMapping("/registration")
    public String performRegistration(@ModelAttribute("person") @Valid SecurityPerson person,
                                      BindingResult bindingResult,
                                      Model model) {
        personValidator.validate(person, bindingResult);

        if (bindingResult.hasErrors()) {
            return "auth/registration";
        }

        String keyFromForm = person.getKey();
        String exp = "";
        try {
            registrationService.register(person, keyFromForm);

        } catch (IncorrectKeyException e) {
            exp = e.getMessage();
            model.addAttribute("exception", exp);
            return "auth/registration";
        }


        return "redirect:/auth/login";
    }
}
