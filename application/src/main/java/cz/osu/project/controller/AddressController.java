package cz.osu.project.controller;

import cz.osu.project.database.entity.Address;
import cz.osu.project.exception.NotFoundException;
import cz.osu.project.exception.UserErrorException;
import cz.osu.project.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class AddressController {
    @Autowired
    AddressService addressService;

    @GetMapping("/addresses")
    public String getAdresses(Model model) {
        List<Address> products = addressService.getAll();
        model.addAttribute("addresses", products);

        return "addresses";
    }

    @GetMapping("/address/{id}")
    public String getAddress(@PathVariable Long id, Model model) {
        Address address = addressService.get(id);
        model.addAttribute("address", address);

        return "address";
    }

    @PostMapping("/address/{id}")
    public String postAddress(@PathVariable Long id,
                              @RequestParam(name="streetName", required=true)String streetName,
                              @RequestParam(name="buildingNumber", required=true)String buildingNumber,
                              @RequestParam(name="postalCode", required=true)String postalCode,
                              @RequestParam(name="city", required=true)String city,
                              @RequestParam(name="state", required=true)String state,
                              Model model) {
        Address address= addressService.get(id);
        address.set(streetName.trim(), buildingNumber.trim(), postalCode.trim(), city.trim(), state.trim());
        try {
            addressService.save(address);
            model.addAttribute("message", "Upraveno");
        }
        catch (UserErrorException e) {
            model.addAttribute("error", e.getMessage());
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
        }

        address = addressService.get(id);
        model.addAttribute("address", address);

        return "address";
    }

    @GetMapping("/address/new")
    public String getNewAddress(Model model) {
        return "address";
    }

    @GetMapping("/address/{id}/delete")
    public String deleteAddress(@PathVariable Long id, Model model) {
        try {
            addressService.delete(id);
        }
        catch (UserErrorException e) {
            model.addAttribute("address", addressService.get(id));
            model.addAttribute("error", e.getMessage());
            return "address";
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
        }

        return "redirect:/addresses";
    }

    @PostMapping("/address/new")
    public String postNewAddress(@RequestParam(name="streetName", required=true)String streetName,
                                 @RequestParam(name="buildingNumber", required=true)String buildingNumber,
                                 @RequestParam(name="postalCode", required=true)String postalCode,
                                 @RequestParam(name="city", required=true)String city,
                                 @RequestParam(name="state", required=true)String state,
                                 Model model) {
        try {
            Address address = addressService.create(streetName, buildingNumber, postalCode, city, state);
            return "redirect:/address/" + address.getId();
        }
        catch (UserErrorException e) {
            model.addAttribute("streetName", streetName.trim());
            model.addAttribute("buildingNumber", buildingNumber.trim());
            model.addAttribute("postalCode", postalCode.trim());
            model.addAttribute("city", city.trim());
            model.addAttribute("state", state.trim());
            model.addAttribute("error", e.getMessage());
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
        }

        return "address";
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
