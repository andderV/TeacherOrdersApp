package ru.andderv.order.TeacherOrdersApp.controllers;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.andderv.order.TeacherOrdersApp.models.ContractsGroceries;
import ru.andderv.order.TeacherOrdersApp.models.Groceries;
import ru.andderv.order.TeacherOrdersApp.models.OrdersToSupplier;
import ru.andderv.order.TeacherOrdersApp.models.OrdersToSuppliersGrocery;
import ru.andderv.order.TeacherOrdersApp.services.ContractsGroceriesService;
import ru.andderv.order.TeacherOrdersApp.services.GroceriesService;
import ru.andderv.order.TeacherOrdersApp.services.OrdersToSuppliersGroceriesService;
import ru.andderv.order.TeacherOrdersApp.services.OrdersToSuppliersService;

/**
 * @author andderV
 * @version 26.01.2025 11:39
 * TeacherOrdersApp
 */
@Controller
@RequestMapping("/orderToSupGrocery")
public class OrderToSuppliersGroceryController {
    private final OrdersToSuppliersGroceriesService ordersGroceriesService;
    private final OrdersToSuppliersService ordersService;
    private final GroceriesService productService;
    private final ContractsGroceriesService contractsGroceriesService;


    public OrderToSuppliersGroceryController(OrdersToSuppliersGroceriesService ordersGroceriesService,
                                             OrdersToSuppliersService ordersService,
                                             GroceriesService product,
                                             ContractsGroceriesService contractsGroceriesService) {
        this.ordersGroceriesService = ordersGroceriesService;
        this.ordersService = ordersService;
        this.productService = product;
        this.contractsGroceriesService = contractsGroceriesService;
    }

    @GetMapping("/{id}")
    public String showItem(@PathVariable("id") int id, Model model) {
        int orderId = ordersGroceriesService.findById(id).getOrder().getId();
        model.addAttribute("item", ordersGroceriesService.findById(id));
        model.addAttribute("fullListItem", ordersGroceriesService.groceryList(ordersService.findById(orderId)));
        return "redirect:/ordersSuppliers/id=" + orderId;
    }

    @GetMapping("/new")
    public String newItem(@RequestParam(value = "orderId", required = false) Integer orderId,
                          @ModelAttribute("item") OrdersToSuppliersGrocery item,
                          @ModelAttribute("product") Groceries grocery,
                          Model model) {
        model.addAttribute("order", ordersService.findById(orderId));
        model.addAttribute("groceries", productService.findAll(true));
        model.addAttribute("fullListItem", ordersGroceriesService.groceryList(ordersService.findById(orderId)));
        return "oGroceries/new";
    }

    @PostMapping
    public String create(@ModelAttribute("item") @Valid OrdersToSuppliersGrocery item,
                         BindingResult bindingResult,
                         @ModelAttribute("product") Groceries grocery,
                         @ModelAttribute("order") OrdersToSupplier orders,
                         Model model) {
        model.addAttribute("order", ordersService.findById(orders.getId()));

        model.addAttribute("groceries", productService.findAll(true));
        model.addAttribute("fullListItem", ordersGroceriesService.groceryList(ordersService.findById(orders.getId())));

        if (bindingResult.hasErrors()) {
            return "groceryItem/new";

        }

        item.setProduct(grocery);
        item.setOrder(orders);
        ordersGroceriesService.save(item);
        return "redirect:/item/new?orderId=" + orders.getId();
    }

//    @GetMapping("/{id}/edit")
//    public String edit(@PathVariable("id") Integer orderId,
//                       @ModelAttribute("item") GroceryItem item,
//                       @ModelAttribute("product") Groceries grocery,
//                       Model model) {
//        model.addAttribute("order", orderService.findById(orderId));
//        model.addAttribute("groceries", groceriesService.findAll(true));
//        model.addAttribute("fullListItem", groceryItemService.groceryItemList(orderService.findById(orderId)));
//        return "groceryItem/edit";
//    }
//
//    @PatchMapping
//    public String addItem(@ModelAttribute("item") @Valid GroceryItem item,
//                          BindingResult bindingResult,
//                          @ModelAttribute("product") Groceries grocery,
//                          @ModelAttribute("order") Orders orders) {
//
//        if (bindingResult.hasErrors()) {
//            return "groceryItem/new";
//
//        }
//
//        item.setGrocery(grocery);
//        item.setOrder(orders);
//        groceryItemService.save(item);
//        return "redirect:/item/" + orders.getOrderId() + "/edit";
//    }
//
//    @DeleteMapping("/{id}")
//    public String delete(@PathVariable("id") int id) {
//        int orderId = groceryItemService.findById(id).getOrder().getOrderId();
//        groceryItemService.delete(id);
//        return "redirect:/orders/" + orderId;
//    }

    @GetMapping("/generalOrder/grocery")
    public String showAllProductWithResultDateBetween(@RequestParam(value = "sortByProductName", required = false, defaultValue = "true")
                                                          boolean sortByProductName,
            @ModelAttribute("emptyOrderToSupGro") OrdersToSuppliersGrocery emptyOrderToSupGro,
            Model model) {
        model.addAttribute("getAllGroceries", contractsGroceriesService.findAll(sortByProductName) );

        return "generalOrder/newOrder";
    }

    @PostMapping("/generalOrder")
    public String create(@ModelAttribute("emptyOrderToSupGro") OrdersToSuppliersGrocery orders){
        return "redirect:/generalOrder/grocery";
    }
}
