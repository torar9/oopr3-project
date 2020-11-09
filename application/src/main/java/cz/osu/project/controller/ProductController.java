package cz.osu.project.controller;

import cz.osu.project.service.AddressService;
import cz.osu.project.service.ContactService;
import cz.osu.project.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
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

    @GetMapping("/greeting")
    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }

    @GetMapping("/newItem")
    public String newItem(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {

        model.addAttribute("name", name);
        return "greeting";
    }
}