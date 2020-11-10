package cz.osu.project.controller;

import cz.osu.project.database.entity.Product;
import cz.osu.project.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller("/product")
public class ProductController {
    @Autowired
    ProductService productService;
    @Autowired
    ContactService contactService;
    @Autowired
    AddressService addressServiceService;
    @Autowired
    CompanyService companyService;
    @Autowired
    StockItemService stockService;

    @GetMapping("/products")
    public String getProducts(Model model) {
        List<Product> products = productService.getAll();
        model.addAttribute("products", products);

        return "products";
    }

    @GetMapping("/greeting")
    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }
}
