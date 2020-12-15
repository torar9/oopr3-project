package cz.osu.project.controller;

import cz.osu.project.database.entity.Company;
import cz.osu.project.database.entity.Product;
import cz.osu.project.exception.NotFoundException;
import cz.osu.project.exception.UserErrorException;
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
    public String getProducts(@RequestParam(value = "search", required = false) String search, Model model) {
        List<Product> products = (search == null || search.isEmpty())? productService.getAll() : productService.searchByName(search);
        model.addAttribute("search", search);
        model.addAttribute("products", products);

        return "products";
    }

    @GetMapping("/product/{id}")
    public String getProduct(@PathVariable Long id, Model model) throws NotFoundException {
        Product product = productService.get(id);
        model.addAttribute("product", product);
        model.addAttribute("isUsed", (product.getStockItems().size() > 0)? true : false);

        return "product";
    }

    @PostMapping("/product/{id}")
    public String postProduct(@PathVariable Long id,
                              @RequestParam(name="name", required=true)String name,
                              @RequestParam(name="desc", required=true)String desc,
                              @RequestParam(name="manufacturer", required=true)String manufacturer,
                              Model model) throws NotFoundException{
        Product product = productService.get(id);
        product.set(name.trim(), desc.trim(), manufacturer.trim());
        try {
            productService.save(product);
            model.addAttribute("message", "Upraveno");
        }
        catch (UserErrorException e) {
            model.addAttribute("error", e.getMessage());
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
        }

        product = productService.get(id);
        model.addAttribute("product", product);
        model.addAttribute("isUsed", (product.getStockItems().size() > 0)? true : false);

        return "product";
    }

    @GetMapping("/product/new")
    public String getNewProduct(Model model) {
        return "product";
    }

    @GetMapping("/product/{id}/delete")
    public String deleteProduct(@PathVariable Long id, Model model) throws NotFoundException{
        try {
            productService.delete(id);
        }
        catch (UserErrorException e) {
            model.addAttribute("product", productService.get(id));
            model.addAttribute("error", e.getMessage());
            return "product";
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
        }

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
        catch (UserErrorException e) {
            model.addAttribute("name", name.trim());
            model.addAttribute("desc", desc.trim());
            model.addAttribute("manufacturer", manufacturer.trim());
            model.addAttribute("error", e.getMessage());
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
        }

        return "product";
    }

    @ExceptionHandler(value = NotFoundException.class)
    public String NotFoundException(NotFoundException e, Model model) {
        return "error";
    }

    @ExceptionHandler(value = Exception.class)
    public String basicException(NotFoundException e, Model model) {
        return "error";
    }
}
