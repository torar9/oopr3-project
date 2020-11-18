package cz.osu.project.controller;

import cz.osu.project.database.entity.Address;
import cz.osu.project.database.entity.Company;
import cz.osu.project.database.entity.Contact;
import cz.osu.project.database.entity.Product;
import cz.osu.project.service.AddressService;
import cz.osu.project.service.CompanyService;
import cz.osu.project.service.ContactService;
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

    @GetMapping("/")
    public String getRoot()
    {
        return "redirect:/companies";
    }

    @GetMapping("/companies")
    public String getCompanies(Model model) {
        List<Company> products = companyService.getAll();
        model.addAttribute("companies", products);

        return "companies";
    }

    @GetMapping("/company/{id}")
    public String getCompany(@PathVariable long id, Model model) {
        Company company = companyService.get(id);
        List<Address> addresses = addressService.getAll();
        List<Contact> contacts = contactService.getAll();

        model.addAttribute("company", company);
        model.addAttribute("addresses", addresses);
        model.addAttribute("contacts", contacts);

        return "company";
    }

    @PostMapping("/company/{id}")
    public String postCompany(@PathVariable long id,
                              @RequestParam(name="name", required=true)String name,
                              @RequestParam(name="address", required=true)long addressID,
                              @RequestParam(name="contact", required=true)long contactID,
                              Model model) {
        Company company = companyService.get(id);
        Address address = addressService.get(addressID);
        Contact contact = contactService.get(contactID);

        List<Address> addresses = addressService.getAll();
        List<Contact> contacts = contactService.getAll();

        model.addAttribute("addresses", addresses);
        model.addAttribute("contacts", contacts);

        company.set(name, address, contact);
        try {
            companyService.save(company);
            model.addAttribute("message", "Upraveno");
        }
        catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }

        company = companyService.get(id);
        model.addAttribute("company", company);

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
    public String deleteProduct(@PathVariable long id, Model model) {
        companyService.delete(id);

        return "redirect:/companies";
    }

    @PostMapping("/company/new")
    public String postNewCompany(@RequestParam(name="name", required=true)String name,
                                 @RequestParam(name="address", required=true)long addressID,
                                 @RequestParam(name="contact", required=true)long contactID,
                                 Model model) {
        List<Address> addresses = addressService.getAll();
        List<Contact> contacts = contactService.getAll();

        model.addAttribute("addresses", addresses);
        model.addAttribute("contacts", contacts);

        try {
            Company company = companyService.create(name, addressID, contactID);
            return "redirect:/company/" + company.getId();
        }
        catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }

        return "company";
    }
}
