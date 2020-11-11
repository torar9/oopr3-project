package cz.osu.project.controller;

import cz.osu.project.database.entity.Product;
import cz.osu.project.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
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

    @GetMapping("/product/{id}")
    public String getProduct(@PathVariable long id, Model model) {
        Product product = productService.get(id);
        model.addAttribute("product", product);

        return "product";
    }

    @PostMapping("/product/{id}")
    public String postProduct(@PathVariable long id,
                              @RequestParam(name="name", required=true)String name,
                              @RequestParam(name="desc", required=true)String desc,
                              @RequestParam(name="manufacturer", required=true)String manufacturer,
                              Model model) {
        Product product = productService.get(id);
        product.set(name, desc, manufacturer);
        productService.save(product);

        product = productService.get(id);
        model.addAttribute("product", product);

        return "product";
    }

    @GetMapping("/greeting")
    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }
}
