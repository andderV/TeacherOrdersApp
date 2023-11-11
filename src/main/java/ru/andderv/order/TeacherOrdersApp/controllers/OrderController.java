package ru.andderv.order.TeacherOrdersApp.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.andderv.order.TeacherOrdersApp.models.Orders;
import ru.andderv.order.TeacherOrdersApp.models.Teacher;
import ru.andderv.order.TeacherOrdersApp.services.OrderService;
import ru.andderv.order.TeacherOrdersApp.services.TeachersService;

/**
 * @author andderV
 * @date 10.11.2023 19:14
 * TeacherOrdersApp
 */
@Controller
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;
    private final TeachersService teachersService;

    @Autowired
    public OrderController(OrderService orderService, TeachersService teachersService) {
        this.orderService = orderService;
        this.teachersService = teachersService;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("orders", orderService.findAll());
        return "order/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("order", orderService.findById(id));
        return "order/show";
    }

    @GetMapping("/new")
    public String newOrder(@ModelAttribute("order") Orders order, @ModelAttribute("owner")Teacher teacher,
                           Model model) {
        model.addAttribute("teachers", teachersService.findAll());
        return "order/new";
    }

    @PostMapping
    public String create(@ModelAttribute("order") @Valid Orders order,
                         BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "order/new";
        }
        orderService.save(order);
        return "redirect:/orders";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("order", orderService.findById(id));
        return "orders/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("order") @Valid Orders order,
                         BindingResult bindingResult,
                         @PathVariable("id") int id) {

        orderService.update(id, order);
        return "redirect:/orders";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        orderService.delete(id);
        return "redirect:/orders";
    }


}
