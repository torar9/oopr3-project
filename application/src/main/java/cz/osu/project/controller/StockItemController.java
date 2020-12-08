package cz.osu.project.controller;

import cz.osu.project.database.entity.Company;
import cz.osu.project.database.entity.Expedition;
import cz.osu.project.database.entity.Product;
import cz.osu.project.database.entity.StockItem;
import cz.osu.project.exception.UserErrorException;
import cz.osu.project.service.CompanyService;
import cz.osu.project.service.ExpeditionService;
import cz.osu.project.service.ProductService;
import cz.osu.project.service.StockItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class StockItemController {
    @Autowired
    StockItemService stockItemService;
    @Autowired
    CompanyService companyService;
    @Autowired
    ProductService productService;
    @Autowired
    ExpeditionService expeditionService;

    @GetMapping("/stockItems")
    public String getStockItems(@RequestParam(value = "search", required = false) String search,
                                @RequestParam(value = "choice", required = false, defaultValue = "1") String choice,  Model model) {
        List<StockItem> stockItems;
        if(search == null || search.isEmpty()) {
            if(choice.equals("1"))
                stockItems = stockItemService.getFreeItems();
            else stockItems = stockItemService.getReservedItems();
        }
        else {
            stockItems = stockItemService.searchByName(search);
        }
        model.addAttribute("search", search);
        model.addAttribute("stockItems", stockItems);
        model.addAttribute("choice", choice);

        return "stockItems";
    }

    @GetMapping("/stockItem/{id}")
    public String getStockItem(@PathVariable long id, Model model) {
        StockItem stockItem = stockItemService.get(id);
        model.addAttribute("stockItem", stockItem);
        List<Product> products = productService.getAll();
        model.addAttribute("products", products);

        List<Expedition> expeditions;
        if(stockItem.getExpedition() == null || stockItem.getExpedition().getStatus().equals("Vytvořeno"))
            expeditions = expeditionService.getOngoinExpeditions();
        else expeditions = expeditionService.getAll();
        model.addAttribute("expeditions" , expeditions);

        List<Company> supliers = companyService.getAll();
        model.addAttribute("supliers" , supliers);

        return "stockItem";
    }

    @PostMapping("/stockItem/{id}")
    public String postStockItem(@PathVariable long id,
                              @RequestParam(name="expedition", required=false)Long expeditionID,
                              @RequestParam(name="suplier", required=true)Long suplierID,
                              @RequestParam(name="product", required=true)Long productID,
                              @RequestParam(name="quantity", required=true)Integer quantity,
                              @RequestParam(name="price", required=true)Double price,
                              @RequestParam(name="weight", required=true)Double weight,
                              Model model) {
        StockItem stockItem = stockItemService.get(id);
        Company suplier = companyService.get(suplierID);
        Product product = productService.get(productID);

        if(expeditionID == null)
            stockItem.set(null, suplier, product, quantity, price, weight);
        else {
            Expedition expedition = expeditionService.get(expeditionID);
            stockItem.set(expedition, suplier, product, quantity, price, weight);
        }

        try {
            stockItemService.save(stockItem);
            model.addAttribute("message", "Upraveno");
        }
        catch (UserErrorException e) {
            model.addAttribute("error", e.getMessage());
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
        }

        stockItem = stockItemService.get(id);
        model.addAttribute("stockItem", stockItem);
        List<Product> products = productService.getAll();
        model.addAttribute("products", products);
        List<Expedition> expeditions = expeditionService.getAll();
        model.addAttribute("expeditions" , expeditions);
        List<Company> supliers = companyService.getAll();
        model.addAttribute("supliers" , supliers);

        return "stockItem";
    }

    @GetMapping("/stockItem/new")
    public String getNewStockItem(Model model) {
        List<Product> products = productService.getAll();
        model.addAttribute("products", products);
        List<Expedition> expeditions = expeditionService.getOngoinExpeditions();
        model.addAttribute("expeditions" , expeditions);
        List<Company> supliers = companyService.getAll();
        model.addAttribute("supliers" , supliers);

        return "stockItem";
    }

    @GetMapping("/stockItem/{id}/delete")
    public String deleteStockItem(@PathVariable long id, Model model) {
        stockItemService.delete(id);

        return "redirect:/stockItems";
    }

    @PostMapping("/stockItem/new")
    public String postNewStockItem(@RequestParam(name="expedition", required=false)Long expeditionID,
                                 @RequestParam(name="suplier", required=false)Long suplierID,
                                 @RequestParam(name="product", required=true)Long productID,
                                 @RequestParam(name="quantity", required=true)int quantity,
                                 @RequestParam(name="price", required=true)Double price,
                                 @RequestParam(name="weight", required=true)Double weight,
                                 Model model) {
        try {
            StockItem stockItem = stockItemService.create(quantity, price, weight, productID, suplierID, expeditionID);
            return "redirect:/stockItem/" + stockItem.getId();
        }
        catch (UserErrorException e) {
            model.addAttribute("quantity", quantity);
            model.addAttribute("price", price);
            model.addAttribute("weight", weight);
            model.addAttribute("error", e.getMessage());
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
        }

        return "stockItem";
    }
}
