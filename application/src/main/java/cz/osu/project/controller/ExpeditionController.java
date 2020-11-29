package cz.osu.project.controller;

import cz.osu.project.database.entity.Company;
import cz.osu.project.database.entity.Expedition;
import cz.osu.project.exception.UserErrorException;
import cz.osu.project.service.CompanyService;
import cz.osu.project.service.ExpeditionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ExpeditionController {
    @Autowired
    ExpeditionService expeditionService;
    @Autowired
    CompanyService companyService;

    @GetMapping("/expeditions")
    public String getExpeditions(Model model) {
        List<Expedition> expeditions = expeditionService.getAll();
        model.addAttribute("expeditions", expeditions);

        return "expeditions";
    }

    @GetMapping("/expedition/{id}")
    public String getExpedition(@PathVariable Long id, Model model) {
        Expedition expedition = expeditionService.get(id);
        model.addAttribute("expedition", expedition);
        List<Company> customers = companyService.getAll();
        model.addAttribute("customers", customers);

        return "expedition";
    }

    @PostMapping("/expedition/{id}")
    public String postExpedition(@PathVariable Long id,
                              @RequestParam(name="customer", required=true)Long customerID,
                              Model model) {
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

        return "redirect:/expeditions";
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
}
