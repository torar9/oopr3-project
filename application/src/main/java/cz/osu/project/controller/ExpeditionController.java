package cz.osu.project.controller;

import cz.osu.project.database.entity.Company;
import cz.osu.project.database.entity.Expedition;
import cz.osu.project.database.entity.StockItem;
import cz.osu.project.exception.NotFoundException;
import cz.osu.project.exception.UserErrorException;
import cz.osu.project.service.CompanyService;
import cz.osu.project.service.ExpeditionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ExpeditionController {
    @Autowired
    ExpeditionService expeditionService;
    @Autowired
    CompanyService companyService;

    @GetMapping("/expeditions")
    public String getExpeditions(@RequestParam(value = "search", required = false) Long search,
                                @RequestParam(value = "choice", required = false, defaultValue = "1") String choice,  Model model) {
        List<Expedition> expeditions;
        if(search == null) {
            if(choice.equals("1"))
                expeditions = expeditionService.getExpeditionsAccordingToStatus("Storno");
            else if(choice.equals("2")) expeditions = expeditionService.getExpeditionsAccordingToStatus("Vytvořeno");
                else expeditions = expeditionService.getExpeditionsAccordingToStatus("Dokončeno");
        }
        else {
            expeditions = expeditionService.searchByID(search);
        }
        model.addAttribute("search", search);
        model.addAttribute("expeditions", expeditions);
        model.addAttribute("choice", choice);

        return "expeditions";
    }

    @GetMapping("/expedition/{id}")
    public String getExpedition(@PathVariable Long id, Model model) throws NotFoundException {
        Expedition expedition = expeditionService.get(id);
        model.addAttribute("expedition", expedition);
        List<Company> customers = companyService.getAll();
        model.addAttribute("customers", customers);
        List<StockItem> expItems = expeditionService.getItemsInExpedition(expedition);
        model.addAttribute("stockItems", expItems);

        return "expedition";
    }

    @PostMapping("/expedition/{id}")
    public String postExpedition(@PathVariable Long id,
                              @RequestParam(name="customer", required=true)Long customerID,
                              Model model) throws NotFoundException{
        Expedition expedition = expeditionService.get(id);
        Company customer = companyService.get(customerID);
        List<Company> customers = companyService.getAll();
        model.addAttribute("customers", customers);
        expedition.set(customer);
        try {
            expeditionService.save(expedition);
            model.addAttribute("message", "Upraveno");
        }
        catch (UserErrorException e) {
            model.addAttribute("error", e.getMessage());
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
        }
        List<StockItem> expItems = expeditionService.getItemsInExpedition(expedition);
        model.addAttribute("stockItems", expItems);
        expedition = expeditionService.get(id);
        model.addAttribute("expedition", expedition);

        return "expedition";
    }

    @GetMapping("/expedition/new")
    public String getNewExpedition(Model model) {
        List<Company> customers = companyService.getAll();
        model.addAttribute("customers", customers);
        return "expedition";
    }

    @GetMapping("/expedition/{id}/storno")
    public String stornoExpedition(@PathVariable Long id, Model model) {
        expeditionService.storno(id);

        return "redirect:/expedition/" + id;
    }

    @GetMapping("/expedition/{id}/complete")
    public String completeExpedition(@PathVariable Long id, Model model) {
        expeditionService.complete(id);

        return "redirect:/expedition/" + id;
    }

    @PostMapping("/expedition/new")
    public String postNewExpedition(@RequestParam(name="customer", required=true)Long customerID,
                                 @RequestParam(name="status", required=true, defaultValue="Vytvořeno")String status,
                                 Model model) {
        try {
            List<Company> customers = companyService.getAll();
            model.addAttribute("customers", customers);
            Expedition expedition = expeditionService.create(status, customerID);
            return "redirect:/expedition/" + expedition.getId();
        }
        catch (UserErrorException e) {
            model.addAttribute("error", e.getMessage());
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
        }

        return "expedition";
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
