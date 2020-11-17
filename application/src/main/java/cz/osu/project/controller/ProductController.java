package cz.osu.project.controller;

import cz.osu.project.database.entity.Product;
import cz.osu.project.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ProductController {
    @Autowired
    ProductService productService;

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
        try {
            productService.save(product);
            model.addAttribute("message", "Upraveno");
        }
        catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }

        product = productService.get(id);
        model.addAttribute("product", product);

        return "product";
    }

    @GetMapping("/product/new")
    public String getNewProduct(Model model) {
        return "product";
    }

    @GetMapping("/product/{id}/delete")
    public String deleteProduct(@PathVariable long id, Model model) {
        productService.delete(id);

        return "redirect:/products";
    }

    @PostMapping("/product/new")
    public String postNewProduct(@RequestParam(name="name", required=true)String name,
                                 @RequestParam(name="desc", required=true)String desc,
                                 @RequestParam(name="manufacturer", required=true)String manufacturer,
                                 Model model) {
        try {
            Product product = productService.create(name, desc, manufacturer);
            return "redirect:/product/" + product.getId();
        }
        catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }

        return "product";
    }
}
