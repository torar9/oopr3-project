package cz.osu.project.controller;

import cz.osu.project.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }

    @GetMapping("/newItem")
    public String newItem(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        /*
        contactService.create("email@tom.cz", "123456789", "12485546");
        addressServiceService.create("Majčovičkovičkovova", "101", "56651", "Ostrava", "Slovakia");
        companyService.create("Company s.r.o.", 1, 1);
        productService.create("jmeno", "popis");
        stockService.create(10, 350.0, 1, 1);*/
        return "greeting";
    }
}
