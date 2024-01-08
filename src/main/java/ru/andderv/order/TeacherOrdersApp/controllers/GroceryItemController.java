package ru.andderv.order.TeacherOrdersApp.controllers;

import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.andderv.order.TeacherOrdersApp.models.Groceries;
import ru.andderv.order.TeacherOrdersApp.models.GroceryItem;
import ru.andderv.order.TeacherOrdersApp.models.Orders;
import ru.andderv.order.TeacherOrdersApp.models.Result;
import ru.andderv.order.TeacherOrdersApp.services.GroceriesService;
import ru.andderv.order.TeacherOrdersApp.services.GroceryItemService;
import ru.andderv.order.TeacherOrdersApp.services.OrderService;

import java.util.Date;


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

//    @GetMapping
//    public String index(@RequestParam(value = "sort", required = false, defaultValue = "true") Boolean sort,
//            Model model) {
//        model.addAttribute("groceriesItem", groceryItemService.findAllWithSorting(sort));
//        return "groceryItem/index";
//    }

    @GetMapping("/{id}")
    public String showItem(@PathVariable("id") int id, Model model) {
        int orderId = groceryItemService.findById(id).getOrder().getOrderId();
        model.addAttribute("item", groceryItemService.findById(id));
        model.addAttribute("fullListItem", groceryItemService.groceryItemList(orderService.findById(orderId)));
        return "groceryItem/show";
    }

    @GetMapping("/new")
    public String newItem(@RequestParam(value = "orderId", required = false) Integer orderId,
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

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") Integer orderId,
                       @ModelAttribute("item") GroceryItem item,
                       @ModelAttribute("product") Groceries grocery,
                       Model model) {
        model.addAttribute("order", orderService.findById(orderId));
        model.addAttribute("groceries", groceriesService.findAll(true));
        model.addAttribute("fullListItem", groceryItemService.groceryItemList(orderService.findById(orderId)));
        return "groceryItem/edit";
    }

    @PatchMapping
    public String addItem(@ModelAttribute("item") @Valid GroceryItem item,
                          BindingResult bindingResult,
                          @ModelAttribute("product") Groceries grocery,
                          @ModelAttribute("order") Orders orders) {

        if (bindingResult.hasErrors()) {
            return "groceryItem/new";

        }

        item.setGrocery(grocery);
        item.setOrder(orders);
        groceryItemService.save(item);
        return "redirect:/item/" + orders.getOrderId() + "/edit";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        int orderId = groceryItemService.findById(id).getOrder().getOrderId();
        groceryItemService.delete(id);
        return "redirect:/orders/" + orderId;
    }

//    @GetMapping("/report/all")
//    public String showAll(@RequestParam(value = "sort", required = false, defaultValue = "true") boolean sortByProductName,
//                          Model model,
//                          @ModelAttribute("result") Result result) {
//        model.addAttribute("productResult", groceryItemService.findAllWithSorting(sortByProductName));
//        return "reports/all";
//    }

//    @GetMapping("/report/group")
//    public String showAllProductWithResult(Model model) {
//        model.addAttribute("productResult", groceryItemService.sumTotalGroceryItemByGrocery());
//        return "reports/group";
//    }

    @GetMapping("/report/order")
    public String showAllOrdersByDateOrderBetweenStartAndEnd(@RequestParam(value = "start", required = false)
                                                             @DateTimeFormat(pattern = "yyyy-MM-dd") Date start,
                                                             @RequestParam(value = "end", required = false)
                                                             @DateTimeFormat(pattern = "yyyy-MM-dd") Date end,
                                                             Model model) {
        model.addAttribute("ordersResult",
                orderService.findAllByDateOrderBetweenStartAndEnd(start, end));

        return "reports/group";
    }

    @GetMapping("/report/grocery")
    public String showAllProductWithResultDateBetween(@RequestParam(value = "start", required = false)
                                                      @DateTimeFormat(pattern = "yyyy-MM-dd") Date start,
                                                      @RequestParam(value = "end", required = false)
                                                      @DateTimeFormat(pattern = "yyyy-MM-dd") Date end,
                                                      Model model,
                                                      @ModelAttribute("result") Result result) {
        model.addAttribute("productResult",
                groceryItemService.sumTotalGroceryItemByGroceryWithDateBetween(start, end));

        return "reports/all";
    }

}
