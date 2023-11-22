package ru.andderv.order.TeacherOrdersApp.controllers;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.andderv.order.TeacherOrdersApp.models.Groceries;
import ru.andderv.order.TeacherOrdersApp.models.GroceryItem;
import ru.andderv.order.TeacherOrdersApp.models.Orders;
import ru.andderv.order.TeacherOrdersApp.services.GroceriesService;
import ru.andderv.order.TeacherOrdersApp.services.GroceryItemService;
import ru.andderv.order.TeacherOrdersApp.services.OrderService;

/**
 * @author andderV
 * @date 18.11.2023 15:51
 * TeacherOrdersApp
 */
@Controller
@RequestMapping("/item")
public class GroceryItemController {
    private final GroceryItemService groceryItemService;
    private final OrderService orderService;
    private final GroceriesService groceriesService;

    public GroceryItemController(GroceryItemService groceryItemService, OrderService orderService,
                                 GroceriesService groceriesService) {
        this.groceryItemService = groceryItemService;
        this.orderService = orderService;
        this.groceriesService = groceriesService;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("groceries", groceryItemService.findAll());
        return "groceryItem/index";
    }

    @GetMapping("/new")
    public String newOrder(@RequestParam(value = "orderId", required = false) Integer orderId,
                           @ModelAttribute("item") GroceryItem item,
                           @ModelAttribute("product") Groceries grocery,
                           Model model) {
        model.addAttribute("order", orderService.findById(orderId));
        model.addAttribute("groceries", groceriesService.findAll(true));
        model.addAttribute("fullListItem", groceryItemService.groceryItemList(orderService.findById(orderId)));
        return "groceryItem/new";
    }

    @PostMapping
    public String create(@ModelAttribute("item") @Valid GroceryItem item,
                         BindingResult bindingResult,
                         @ModelAttribute("product") Groceries grocery,
                         @ModelAttribute("order") Orders orders,
                         Model model) {
        model.addAttribute("order", orderService.findById(orders.getOrderId()));
        model.addAttribute("groceries", groceriesService.findAll(true));
        model.addAttribute("fullListItem", groceryItemService.groceryItemList(orderService.findById(orders.getOrderId())));

        if (bindingResult.hasErrors()) {
            return "groceryItem/new";

        }

        item.setGrocery(grocery);
        item.setOrder(orders);
        groceryItemService.save(item);
        return "redirect:/item/new?orderId=" + orders.getOrderId();
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        groceriesService.delete(id);
        return "redirect:/item";
    }

}
