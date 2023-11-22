package ru.andderv.order.TeacherOrdersApp.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.andderv.order.TeacherOrdersApp.models.Orders;
import ru.andderv.order.TeacherOrdersApp.models.Teacher;
import ru.andderv.order.TeacherOrdersApp.services.*;

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

    private final GroceryItemService groceryItemService;

    @Autowired
    public OrderController(OrderService orderService, TeachersService teachersService, GroceryItemService groceryItemService) {
        this.orderService = orderService;
        this.teachersService = teachersService;
        this.groceryItemService = groceryItemService;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("orders", orderService.findAll());
        return "order/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("order", orderService.findById(id));
        model.addAttribute("groceryItem", groceryItemService.groceryItemList(orderService.findById(id)));
        return "order/show";
    }

    @GetMapping("/new")
    public String newOrder(@ModelAttribute("order") Orders order, @ModelAttribute("owner") Teacher teacher,
                           Model model) {
        model.addAttribute("teachers", teachersService.findAll());
        return "order/new";
    }

    @PostMapping
    public String create(@ModelAttribute("order") @Valid Orders order,
                         BindingResult bindingResult,
                         @ModelAttribute("owner") Teacher owner,
                         Model model) {
        model.addAttribute("teachers", teachersService.findAll());


        if(bindingResult.hasErrors()){
            return "order/new";
        }

        order.setOwner(owner);
        orderService.save(order);
        int id = order.getOrderId();
        return "redirect:/item/new?orderId=" + id;
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("order", orderService.findById(id));
        return "order/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("order") @Valid Orders order,
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
