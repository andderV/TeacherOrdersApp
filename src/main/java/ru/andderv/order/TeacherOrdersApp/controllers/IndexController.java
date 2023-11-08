package ru.andderv.order.TeacherOrdersApp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author andderV
 * @date 30.10.2023 18:35
 * TeacherOrdersApp
 */
@Controller
@RequestMapping("/index")
public class IndexController {
    @GetMapping("")
    public String index(){
        return "index";
    }
}
