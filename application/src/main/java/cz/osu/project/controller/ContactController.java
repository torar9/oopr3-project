package cz.osu.project.controller;

import cz.osu.project.database.entity.Contact;
import cz.osu.project.database.entity.Product;
import cz.osu.project.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ContactController {
    @Autowired
    ContactService contactService;

    @GetMapping("/contacts")
    public String getContacts(Model model) {
        List<Contact> products = contactService.getAll();
        model.addAttribute("contacts", products);

        return "contacts";
    }

    @GetMapping("/contact/{id}")
    public String getContact(@PathVariable long id, Model model) {
        Contact contact = contactService.get(id);
        model.addAttribute("contact", contact);

        return "contact";
    }

    @PostMapping("/contact/{id}")
    public String postContact(@PathVariable long id,
                              @RequestParam(name="email", required=true)String email,
                              @RequestParam(name="phone", required=true)String phone,
                              @RequestParam(name="fax", required=false)String fax,
                              Model model) {
        Contact contact = contactService.get(id);
        contact.set(email, phone, fax);
        try {
            contactService.save(contact);
            model.addAttribute("message", "Upraveno");
        }
        catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }

        contact = contactService.get(id);
        model.addAttribute("contact", contact);

        return "contact";
    }

    @GetMapping("/contact/new")
    public String getNewContact(Model model) {
        return "contact";
    }

    @GetMapping("/contact/{id}/delete")
    public String getNewContact(@PathVariable long id, Model model) {
        contactService.delete(id);

        return "redirect:/contacts";
    }

    @DeleteMapping("/contact/{id}")
    public String deleteContact(@PathVariable long id)
    {
        contactService.delete(id);

        return "redirect:/contacts";
    }

    @PostMapping("/contact/new")
    public String postNewContact(@RequestParam(name="email", required=true)String email,
                                 @RequestParam(name="phone", required=true)String phone,
                                 @RequestParam(name="fax", required=false)String fax,
                                 Model model) {
        try {
            Contact contact = contactService.create(email, phone, fax);
            return "redirect:/contact/" + contact.getId();
        }
        catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }

        return "contact";
    }
}
