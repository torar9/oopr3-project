package cz.osu.project.controller;

import cz.osu.project.database.entity.Address;
import cz.osu.project.database.entity.Company;
import cz.osu.project.database.entity.Contact;
import cz.osu.project.database.entity.Expedition;
import cz.osu.project.exception.UserErrorException;
import cz.osu.project.service.AddressService;
import cz.osu.project.service.CompanyService;
import cz.osu.project.service.ContactService;
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
public class CompanyController {
    @Autowired
    CompanyService companyService;
    @Autowired
    AddressService addressService;
    @Autowired
    ContactService contactService;
    @Autowired
    ExpeditionService expeditionService;

    @GetMapping("/")
    public String getRoot()
    {
        return "redirect:/companies";
    }

    @GetMapping("/companies")
    public String getCompanies(@RequestParam(value = "search", required = false) String search, Model model) {
        List<Company> companies = (search == null || search.isEmpty())? companyService.getAll() : companyService.searchByName(search);
        model.addAttribute("search", search);
        model.addAttribute("companies", companies);

        return "companies";
    }

    @GetMapping("/company/{id}")
    public String getCompany(@PathVariable Long id, Model model) {
        Company company = companyService.get(id);
        List<Address> addresses = addressService.getAll();
        List<Contact> contacts = contactService.getAll();
        List<Expedition> expeditions = expeditionService.getCompanyExpeditions(company);

        model.addAttribute("company", company);
        model.addAttribute("addresses", addresses);
        model.addAttribute("contacts", contacts);
        model.addAttribute("expeditions", expeditions);

        return "company";
    }

    @PostMapping("/company/{id}")
    public String postCompany(@PathVariable Long id,
                              @RequestParam(name="name", required=true)String name,
                              @RequestParam(name="streetName", required=true)String streetName,
                              @RequestParam(name="buildingNumber", required=true)String buildingNumber,
                              @RequestParam(name="postalCode", required=true)String postalCode,
                              @RequestParam(name="city", required=true)String city,
                              @RequestParam(name="state", required=true)String state,
                              @RequestParam(name="email", required=true)String email,
                              @RequestParam(name="phone", required=true)String phone,
                              @RequestParam(name="fax", required=false)String fax,
                              Model model) {
        Company company = companyService.get(id);
        try {
            company.setName(name);
            Address address = addressService.get(company.getAddress().getId());
            address.set(streetName, buildingNumber, postalCode, city, state);
            addressService.save(address);
            Contact contact = contactService.get(company.getContact().getId());
            contact.set(email, phone, fax);
            contactService.save(contact);

            company = companyService.save(company);
            model.addAttribute("message", "Upraveno");
        }
        catch (UserErrorException e) {
            model.addAttribute("error", e.getMessage());
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
        }

        model.addAttribute("company", company);
        List<Expedition> expeditions = expeditionService.getCompanyExpeditions(company);
        model.addAttribute("expeditions", expeditions);

        return "company";
    }

    @GetMapping("/company/new")
    public String getNewCompany(Model model) {
        List<Address> addresses = addressService.getAll();
        List<Contact> contacts = contactService.getAll();

        model.addAttribute("addresses", addresses);
        model.addAttribute("contacts", contacts);

        return "company";
    }

    @GetMapping("/company/{id}/delete")
    public String deleteProduct(@PathVariable Long id, Model model) {
        try {
            companyService.delete(id);
        }
        catch (UserErrorException e) {
            List<Address> addresses = addressService.getAll();
            List<Contact> contacts = contactService.getAll();
            List<Expedition> expeditions = expeditionService.getCompanyExpeditions(companyService.get(id));

            model.addAttribute("expeditions", expeditions);
            model.addAttribute("addresses", addresses);
            model.addAttribute("contacts", contacts);
            model.addAttribute("company", companyService.get(id));
            model.addAttribute("error", e.getMessage());
            return "company";
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
        }

        return "redirect:/companies";
    }

    @PostMapping("/company/new")
    public String postNewCompany(@RequestParam(name="name", required=true)String name,
                                 @RequestParam(name="streetName", required=true)String streetName,
                                 @RequestParam(name="buildingNumber", required=true)String buildingNumber,
                                 @RequestParam(name="postalCode", required=true)String postalCode,
                                 @RequestParam(name="city", required=true)String city,
                                 @RequestParam(name="state", required=true)String state,
                                 @RequestParam(name="email", required=true)String email,
                                 @RequestParam(name="phone", required=true)String phone,
                                 @RequestParam(name="fax", required=false)String fax,
                                 Model model) {
        List<Address> addresses = addressService.getAll();
        List<Contact> contacts = contactService.getAll();

        model.addAttribute("addresses", addresses);
        model.addAttribute("contacts", contacts);

        try {
            Contact contact = contactService.create(email, phone, fax);
            Address address = addressService.create(streetName, buildingNumber, postalCode, city, state);
            Company company = companyService.create(name, address.getId(), contact.getId());
            return "redirect:/company/" + company.getId();
        }
        catch (UserErrorException e) {
            model.addAttribute("name", name);
            model.addAttribute("error", e.getMessage());
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
        }

        return "company";
    }
}
