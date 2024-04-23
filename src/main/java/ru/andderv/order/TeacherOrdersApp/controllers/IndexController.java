package ru.andderv.order.TeacherOrdersApp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.andderv.order.TeacherOrdersApp.models.Teacher;
import ru.andderv.order.TeacherOrdersApp.services.TeachersService;

/**
 * @author andderV
 * @date 30.10.2023 18:35
 * TeacherOrdersApp
 */
@Controller
@RequestMapping("/index")
public class IndexController {
    private final TeachersService teachersService;

    @Autowired
    public IndexController(TeachersService teachersService) {
        this.teachersService = teachersService;
    }

    @GetMapping("")
    public String index(Model model, Authentication auth){
        String userName = auth.getName();
        Teacher teacher = teachersService.findTeacherByUserName(userName);
        String[] str = teacher.getTeacherName().split(" ");
        StringBuilder firstNameWithPatronymic = new StringBuilder();
        firstNameWithPatronymic.append(str[1]).append(" ").append(str[2]);
        model.addAttribute("firstNameWithPatronymic", firstNameWithPatronymic);
        return "index";
    }
}
