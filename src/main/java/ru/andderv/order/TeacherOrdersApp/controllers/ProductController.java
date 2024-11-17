package ru.andderv.order.TeacherOrdersApp.controllers;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.andderv.order.TeacherOrdersApp.models.Groceries;
import ru.andderv.order.TeacherOrdersApp.models.MeasureUnit;
import ru.andderv.order.TeacherOrdersApp.models.ProductWithBasicUnitMeasurement;
import ru.andderv.order.TeacherOrdersApp.services.GroceriesService;
import ru.andderv.order.TeacherOrdersApp.services.MeasureUnitService;
import ru.andderv.order.TeacherOrdersApp.util.ProductValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author andderV
 * @date 29.10.2023 19:12
 * TeacherOrdersApp
 */
@Controller
@RequestMapping("/groceries")
public class ProductController {
    private final GroceriesService groceriesService;
    private final MeasureUnitService unitService;
    private final ProductValidator productValidator;

    @Autowired
    public ProductController(GroceriesService groceriesService, MeasureUnitService unitService, ProductValidator productValidator) {
        this.groceriesService = groceriesService;
        this.unitService = unitService;
        this.productValidator = productValidator;
    }

    @GetMapping
    public String index(@RequestParam(value = "page", required = false, defaultValue = "0") @Min(0) Integer page,
                        @RequestParam(value = "product_per_page", required = false, defaultValue = "20") @Min(1) @Max(100) Integer productPerPage,
                        @RequestParam(value = "sort_by_product_name", required = false, defaultValue = "true") boolean sortByProductName,
                        Model model, Sort sort) {

        List<Groceries> groceries;

        Page<Groceries> pageGroceries = groceriesService.findAll(page, productPerPage, sortByProductName);


        groceries = pageGroceries.getContent();

//        Page<Groceries> groceriesPage = groceriesService.findAll(page, productPerPage, sortByProductName);
//        if (page == null || productPerPage == null) {
//            model.addAttribute("groceries", groceriesService.findAll(sortByProductName));
//
//
//
//        } else {


//        model.addAttribute("groceries", groceriesService
//                .findAllWithPaginationAndSorting(page, productPerPage, sortByProductName));
        List<Groceries> list = groceriesService
                .findAllWithPaginationAndSorting(page, productPerPage, sortByProductName);
        List<ProductWithBasicUnitMeasurement>result = new ArrayList<>();
        for (Groceries gr : list) {
            Optional<MeasureUnit> unit = unitService.findMeasureUnitsByGroceriesId(gr.getId()).stream()
                    .findAny();
            if (unit.isPresent()) {
                int groupId = unit.get().getGroup().getId();
                MeasureUnit basic = unitService.findMeasureUnitByGroupIdAndRatioIs(groupId, 1);
                ProductWithBasicUnitMeasurement product = new ProductWithBasicUnitMeasurement();
                product.setProductId(gr.getId());
                product.setProductName(gr.getProductName());
                product.setBasicUnit(basic);
                result.add(product);

            }

        }

        model.addAttribute("groceries", result);
        model.addAttribute("page", pageGroceries);
        model.addAttribute("currentPage", pageGroceries.getNumber() + 1);
        model.addAttribute("totalItems", pageGroceries.getTotalElements());
        model.addAttribute("totalPages", pageGroceries.getTotalPages());
        model.addAttribute("pageSize", groceries.size());
        return "groceries/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("product", groceriesService.findById(id));
        model.addAttribute("orders", groceriesService.getOrdersByProductId(id));
        model.addAttribute("units", groceriesService.getMeasureUnitsByProductId(id));
        Optional<MeasureUnit> unit = unitService.findMeasureUnitsByGroceriesId(id).stream()
                .findAny();

        if (unit.isPresent()) {
            int groupId = unit.get().getGroup().getId();
            model.addAttribute("unit", unitService.findMeasureUnitByGroupIdAndRatioIs(groupId, 1));

        }
        model.addAttribute("contracts", groceriesService.getContractsByProductId(id));
        model.addAttribute("ordersToSuppliers", groceriesService.getOrdersToSuppliersByProductId(id));
        return "groceries/show";
    }

    @GetMapping("/new")
    public String newProduct(@ModelAttribute("product") Groceries product,
                             @ModelAttribute("unit") MeasureUnit unit,
                             Model model) {
        model.addAttribute("units", unitService.findAll());

        return "groceries/new";
    }

    @PostMapping
    public String create(@ModelAttribute("product") @Valid Groceries product,
                         BindingResult bindingResult,
                         @ModelAttribute("unit") MeasureUnit id,
                         Model model) {

        productValidator.validate(product, bindingResult);


        if (bindingResult.hasErrors()) {
            model.addAttribute("units", unitService.findAll());
            return "groceries/new";
        }

        MeasureUnit unit = unitService.findById(id.getMeasureUnitId());

        product.getMeasureUnits().add(unit);

        groceriesService.save(product);
        return "redirect:/groceries";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("product", groceriesService.findById(id));
        return "groceries/edit";
    }

//    @PostMapping("/add")
//    public String addToCart(Model model, @ModelAttribute) {
//        return
//    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("product") @Valid Groceries product,
                         BindingResult bindingResult,
                         @PathVariable("id") int id) {

        if (bindingResult.hasErrors()) {
            return "groceries/edit";
        }

        groceriesService.update(id, product);
        return "redirect:/groceries";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        groceriesService.delete(id);
        return "redirect:/groceries";
    }


}
